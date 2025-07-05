package com.photo.server.starsnap.adapter_infrastructure.auth.repository

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.EmailTokenEntity
import com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata.EmailTokenCrudRepository
import com.photo.server.starsnap.domain.auth.entity.EmailToken
import com.photo.server.starsnap.domain.auth.repository.EmailTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class EmailTokenRepositoryImpl(
    private val emailTokenCrudRepository : EmailTokenCrudRepository
): EmailTokenRepository {
    override fun save(emailToken: EmailToken): EmailToken {
        val emailTokenEntity = EmailTokenEntity(email = emailToken.email, token = emailToken.token)
        return emailTokenCrudRepository.save(emailTokenEntity).let {
            EmailToken(
                email = it.email,
                token = it.token
            )
        }
    }

    override fun delete(emailToken: EmailToken) {
        val emailTokenEntity = EmailTokenEntity(email = emailToken.email, token = emailToken.token)
        return emailTokenCrudRepository.delete(emailTokenEntity)
    }

    override fun findByIdOrNull(email: String): EmailToken? {
        val emailTokenEntity = emailTokenCrudRepository.findByIdOrNull(email)
        return emailTokenEntity?.let {
            EmailToken(
                email = it.email,
                token = it.token
            )
        }
    }

}