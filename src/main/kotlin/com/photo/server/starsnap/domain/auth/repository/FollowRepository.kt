package com.photo.server.starsnap.domain.auth.repository

import com.photo.server.starsnap.domain.user.entity.FollowEntity
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: Neo4jRepository<FollowEntity, String>