package com.photo.server.starsnap.adapter_usecase.snap.usecase

import com.photo.server.starsnap.adapter_infrastructure.snap.repository.TagRepositoryImpl
import com.photo.server.starsnap.domain.snap.entity.Tag
import com.photo.server.starsnap.usecase.snap.usecase.TagUseCase
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId

@Service
class TagUseCaseImpl(
    private val tagRepositoryImpl: TagRepositoryImpl
): TagUseCase {
    // 태그 생성
    override fun createTag(tag: String): Tag {
        val tag = Tag(
            id = NanoId.generate(16),
            name = tag,
        )
        tag.count += 1
        return tagRepositoryImpl.save(tag)
    }

    // 태그 조회
    override fun getTag(tag: String): Tag {
        val tag = tagRepositoryImpl.findByName(tag) ?: createTag(tag)
        tag.count += 1
        return tagRepositoryImpl.save(tag)
    }
}