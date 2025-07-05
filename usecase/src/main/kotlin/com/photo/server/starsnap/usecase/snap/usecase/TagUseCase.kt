package com.photo.server.starsnap.usecase.snap.usecase

import com.photo.server.starsnap.domain.snap.entity.Tag


interface TagUseCase {
    fun createTag(tag: String): Tag

    fun getTag(tag: String): Tag
}