package com.photo.server.starsnap.usecase.auth.usecase

import com.photo.server.starsnap.usecase.auth.dto.EmailDto
import com.photo.server.starsnap.usecase.auth.dto.EmailResponseDto
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import jakarta.mail.internet.MimeMessage


interface EmailUserCase {
    fun send(email: String): StatusDto

    fun verify(emailDto: EmailDto): EmailResponseDto

    fun checkValidVerifyCode(token: String, email: String)

    fun deleteToken(token: String, email: String)

    fun setContext(verifyCode: String): String

    fun createToken(): String

    fun createVerifyCode(): String

    fun createEmailFrom(email: String): MimeMessage
}