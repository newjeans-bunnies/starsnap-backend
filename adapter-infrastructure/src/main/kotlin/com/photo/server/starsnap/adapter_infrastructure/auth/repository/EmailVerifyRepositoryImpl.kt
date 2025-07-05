package com.photo.server.starsnap.adapter_infrastructure.auth.repository

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.EmailVerifyEntity
import com.photo.server.starsnap.adapter_infrastructure.auth.repository.springdata.EmailVerifyCrudRepository
import com.photo.server.starsnap.domain.auth.entity.EmailVerify
import com.photo.server.starsnap.domain.auth.repository.EmailVerifyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class EmailVerifyRepositoryImpl(
    private val emailVerifyCrudRepository: EmailVerifyCrudRepository
): EmailVerifyRepository {
    override fun findByIdOrNull(id: String): EmailVerify? {
        val emailVerifyEntity = emailVerifyCrudRepository.findByIdOrNull(id)
        return emailVerifyEntity?.let {
            EmailVerify(
                email = it.email,
                verifyCode = it.verifyCode
            )
        }
    }

    override fun delete(emailVerify: EmailVerify) {
        val emailVerifyEntity = EmailVerifyEntity(
            email = emailVerify.email,
            verifyCode = emailVerify.verifyCode
        )
        emailVerifyCrudRepository.delete(emailVerifyEntity)
    }

    override fun save(emailVerify: EmailVerify): EmailVerify {
        val emailVerifyEntity = EmailVerifyEntity(
            email = emailVerify.email,
            verifyCode = emailVerify.verifyCode
        )
        return emailVerifyCrudRepository.save(emailVerifyEntity).let {
            EmailVerify(
                email = it.email,
                verifyCode = it.verifyCode
            )
        }
    }


}