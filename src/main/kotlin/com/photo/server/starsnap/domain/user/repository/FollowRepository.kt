package com.photo.server.starsnap.domain.user.repository

import com.photo.server.starsnap.domain.auth.FollowEntity
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: Neo4jRepository<FollowEntity, String>