package com.photo.server.starsnap.adapter_usecase.auth.usecase

import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.exception.auth.error.exception.ExistEmailException
import com.photo.server.starsnap.exception.auth.error.exception.ExistUserNameException
import com.photo.server.starsnap.exception.auth.error.exception.InvalidEmailFormatException
import com.photo.server.starsnap.exception.auth.error.exception.InvalidUserNameFormatException
import com.photo.server.starsnap.usecase.auth.usecase.ValidUseCase
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.validation.Pattern
import org.springframework.stereotype.Service

@Service
class ValidUseCaseImpl(
    private val userRepositoryImpl: UserRepositoryImpl
): ValidUseCase {
    // 유효한 닉네임인지 검사
    override fun validUsername(username: String): StatusDto {
        val usernameExists = userRepositoryImpl.existsByUsername(username)
        if(usernameExists) throw ExistUserNameException
        if (!Pattern.USERNAME.toPattern().matcher(username).matches()) throw InvalidUserNameFormatException

        return StatusDto("사용가능한 닉네임입니다.", 200)
    }

    // 유효한 이메일인지 검사
    override fun validEmail(email: String): StatusDto {
        val emailExists = userRepositoryImpl.existsByEmail(email)
        if(emailExists) throw ExistEmailException
        if(!Pattern.EMAIL.toPattern().matcher(email).matches()) throw InvalidEmailFormatException

        return StatusDto("사용가능한 이메일입니다", 200)
    }
}