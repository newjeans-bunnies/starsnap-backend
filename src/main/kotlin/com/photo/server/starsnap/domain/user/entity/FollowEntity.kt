package com.photo.server.starsnap.domain.user.entity

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("follow")
data class FollowEntity(
    @Id
    val userId: String,
    var followers: Int,
    var follow: Int,
    @Relationship(type = "FRIEND_OF")
    var follower: List<FollowEntity> = emptyList()
)