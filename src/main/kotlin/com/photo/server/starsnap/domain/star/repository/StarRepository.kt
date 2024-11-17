package com.photo.server.starsnap.domain.star.repository

import org.hibernate.sql.ast.tree.expression.Star
import org.springframework.data.repository.CrudRepository

interface StarRepository: CrudRepository<Star, String> {
}