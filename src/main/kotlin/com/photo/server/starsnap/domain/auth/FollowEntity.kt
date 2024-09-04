package com.photo.server.starsnap.domain.auth


import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class FollowEntity(
    @Id
    val userId: String,
    @Relationship
    val follow: List<FollowEntity> = emptyList()
)
