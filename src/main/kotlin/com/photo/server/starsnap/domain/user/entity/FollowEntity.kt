package com.photo.server.starsnap.domain.user.entity


import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("follow")
data class FollowEntity(
    @Id
    val userId: String,
    @Relationship(type = "FRIEND_OF")
    var follow: List<FollowEntity> = emptyList()
)