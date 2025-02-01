package com.photo.server.starsnap.domain.snap.service

import com.photo.server.starsnap.domain.snap.dto.CreateSnapRequestDto
import com.photo.server.starsnap.domain.snap.dto.GetSnapResponseDto
import com.photo.server.starsnap.domain.snap.entity.SnapEntity
import com.photo.server.starsnap.domain.snap.dto.SnapResponseDto
import com.photo.server.starsnap.domain.snap.dto.UpdateSnapRequestDto
import com.photo.server.starsnap.domain.snap.entity.TagEntity
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundSnapIdException
import com.photo.server.starsnap.domain.snap.error.exception.NotFoundTagException
import com.photo.server.starsnap.domain.snap.error.exception.UnsupportedFileTypeException
import com.photo.server.starsnap.domain.snap.repository.SnapRepository
import com.photo.server.starsnap.domain.snap.repository.TagRepository
import com.photo.server.starsnap.global.utils.type.toType
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.domain.user.repository.BlackUserRepository
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.toSnapDto
import com.photo.server.starsnap.global.dto.toSnapUserDto
import com.photo.server.starsnap.global.error.exception.InvalidRoleException
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
    private val snapAwsS3Service: SnapAwsS3Service,
    private val blackUserRepository: BlackUserRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createSnap(
        userData: UserEntity,
        snapDto: CreateSnapRequestDto,
    ) {
        if (snapDto.image.contentType.toType().name.isValid()) throw UnsupportedFileTypeException
        val imageKey = NanoId.generate(16)
        try {
            val bufferedImage: BufferedImage = ImageIO.read(snapDto.image.inputStream)
            snapAwsS3Service.uploadImage(snapDto.image, imageKey)
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
                aiState = snapDto.aiState
            )
            snapRepository.save(snapData)

        } catch (e: IOException) {
            throw e
        }

    }

    fun deleteSnap(userId: String, snapId: String) {
        val snap = snapRepository.findByIdOrNull(snapId) ?: throw NotFoundSnapIdException

        if (snap.user.id != userId) throw InvalidRoleException

        snap.state = false
        snapRepository.save(snap)
    }

    @Transactional
    fun updateSnap(
        userId: String,
        snapDto: UpdateSnapRequestDto
    ): SnapResponseDto {
        val snapData = snapRepository.findByIdOrNull(snapDto.snapId) ?: throw NotFoundSnapIdException

        if (snapData.user.id != userId) throw InvalidRoleException
        if (snapDto.image != null) snapAwsS3Service.updateImage(snapDto.image, snapData.imageKey)

        snapData.title = snapDto.title
        snapData.source = snapDto.source
        snapData.dateTaken = snapDto.dateTaken

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
            user = user
        )

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


}