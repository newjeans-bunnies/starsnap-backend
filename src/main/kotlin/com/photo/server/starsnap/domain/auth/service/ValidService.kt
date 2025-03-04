package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.error.exception.ExistEmailException
import com.photo.server.starsnap.domain.auth.error.exception.ExistUserNameException
import com.photo.server.starsnap.domain.auth.error.exception.InvalidEmailFormatException
import com.photo.server.starsnap.domain.auth.error.exception.InvalidUserNameFormatException
import com.photo.server.starsnap.domain.user.repository.UserRepository
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.utils.pattern.Pattern
import org.springframework.stereotype.Service

@Service
class ValidService(
    private val userRepository: UserRepository
) {
    fun validUsername(username: String): StatusDto {
        val usernameExists = userRepository.existsByUsername(username)
        if(usernameExists) throw ExistUserNameException
        if (!Pattern.USERNAME.toPattern().matcher(username).matches()) throw InvalidUserNameFormatException

        return StatusDto("사용가능한 닉네임입니다.", 200)
    }

    fun validEmail(email: String): StatusDto {
        val emailExists = userRepository.existsByEmail(email)
        if(emailExists) throw ExistEmailException
        if(!Pattern.EMAIL.toPattern().matcher(email).matches()) throw InvalidEmailFormatException

        return StatusDto("사용가능한 이메일입니다", 200)
    }
}