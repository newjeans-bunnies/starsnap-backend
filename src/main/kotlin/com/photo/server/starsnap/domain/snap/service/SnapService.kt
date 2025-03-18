package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.dto.*
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.snap.entity.TagEntity
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundSnapException
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundTagException
import com.photo.server.starsnap.domain.snap.error.exception.UnsupportedFileTypeException
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.snap.repository.TagRepository
import com.photo.server.starsnap.domain.star.error.exception.NotFoundStarIdException
import com.photo.server.starsnap.domain.star.repository.StarGroupRepository
import com.photo.server.starsnap.domain.star.repository.StarRepository
import com.photo.server.starsnap.global.utils.type.toType
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.BlackUserRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.toSnapDto
import com.photo.server.starsnap.global.dto.toSnapUserDto
import com.photo.server.starsnap.global.error.exception.InvalidRoleException
import com.photo.server.starsnap.global.service.AwsS3Service
import com.photo.server.starsnap.global.utils.type.isValid
import io.viascom.nanoid.NanoId
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

@Service
class SnapService(
    private val snapRepository: SnapRepository,
    private val tagRepository: TagRepository,
    private val awsS3Service: AwsS3Service,
    private val blackUserRepository: BlackUserRepository,
    private val userRepository: UserRepository,
    private val starRepository: StarRepository,
    private val starGroupRepository: StarGroupRepository
) {

    @Transactional
    fun createSnap(
        userData: UserEntity,
        snapDto: CreateSnapRequestDto,
    ) {
        val stars = snapDto.starId.map { starRepository.findByIdOrNull(it) ?: throw NotFoundStarIdException }
        val starGroups =
            snapDto.starGroupId.map { starGroupRepository.findByIdOrNull(it) ?: throw NotFoundStarIdException }
        if (snapDto.image.contentType.toType().name.isValid()) throw UnsupportedFileTypeException
        val imageKey = "snap/" + NanoId.generate(32)
        try {
            val bufferedImage: BufferedImage = ImageIO.read(snapDto.image.inputStream)
            awsS3Service.uploadFileToS3(snapDto.image, imageKey, userData.id)
            val snapData = SnapEntity(
                title = snapDto.title,
                imageSize = snapDto.image.size,
                imageType = snapDto.image.contentType.toType(),
                source = snapDto.source,
                dateTaken = snapDto.dateToken,
                imageKey = imageKey,
                imageWidth = bufferedImage.width,
                imageHeight = bufferedImage.height,
                user = userData,
                tags = createTags(snapDto.tags),
                state = true,
                likeCount = 0,
                aiState = snapDto.aiState,
                starGroup = starGroups,
                star = stars
            )
            snapRepository.save(snapData)

        } catch (e: IOException) {
            throw e
        }

    }

    // snap state false로 변경
    fun deleteSnap(userId: String, snapId: String) {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException

        if (snap.user.id != userId) throw InvalidRoleException

        snap.state = false
        snapRepository.save(snap)
    }

    @Transactional
    fun updateSnap(
        userId: String,
        snapDto: UpdateSnapRequestDto,
        userData: UserEntity
    ): SnapResponseDto {
        val snapData = snapRepository.findByIdOrNull(snapDto.snapId) ?: throw NotFoundSnapIdException

        val stars = snapDto.starId.map { starRepository.findByIdOrNull(it) ?: throw NotFoundStarIdException }
        val starGroups =
            snapDto.starGroupId.map { starGroupRepository.findByIdOrNull(it) ?: throw NotFoundStarIdException }

        if (snapData.user.id != userId) throw InvalidRoleException
        awsS3Service.uploadFileToS3(snapDto.image, snapData.imageKey, userData.id)

        with (snapData) {
            title = snapDto.title
            source = snapDto.source
            dateTaken = snapDto.dateTaken
            starGroup = starGroups
            star = stars
        }

        snapRepository.save(snapData)

        return SnapResponseDto(
            createdUser = snapData.user.toSnapUserDto(),
            snapData = snapData.toSnapDto()
        )
    }

    fun getSnap(getSnapResponseDto: GetSnapResponseDto, userData: UserEntity?): Slice<SnapResponseDto> {
        val pageRequest = PageRequest.of(
            getSnapResponseDto.page, getSnapResponseDto.size, Sort.by(
                Sort.Direction.DESC, "createdAt"
            )
        )
        val user = userRepository.findByIdOrNull(getSnapResponseDto.user)
        val blockUser = if (userData != null) blackUserRepository.findUserBy(userData) else null
        val snapData = snapRepository.findFilteredSnaps(
            pageable = pageRequest,
            state = true,
            blockUser = blockUser,
            tags = getSnapResponseDto.tag,
            title = getSnapResponseDto.title,
            userId = user?.id ?: ""
        ) ?: throw NotFoundSnapException

        return snapData.map {
            SnapResponseDto(
                createdUser = it.user.toSnapUserDto(),
                snapData = it.toSnapDto()
            )
        }
    }

    private fun createTags(tags: List<String>): List<TagEntity> {
        return tags.map {
            if (!tagRepository.existsByName(it)) {
                tagRepository.save(TagEntity(name = it))
            } else {
                tagRepository.findByName(it) ?: throw NotFoundTagException
            }
        }
    }

    // snapId를 통해 photo 다운 URL 생성
    fun getSnapPhoto(snapId: String): GetSnapPhotoResponseDto {
        val snapData = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException
        val presignedUrl = awsS3Service.getPresignedUrl(snapData.imageKey)
        return GetSnapPhotoResponseDto(presignedUrl)
    }

}