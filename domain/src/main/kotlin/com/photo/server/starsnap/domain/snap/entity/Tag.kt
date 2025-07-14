package com.photo.server.starsnap.domain.snap.entity

import com.photo.server.starsnap.domain.snap.entity.base.BaseTag

data class Tag(
    var count: Int = 0,
    val name: String,
    override val id: String
) : BaseTag(id){
    val snaps: List<Snap> = mutableListOf()
}