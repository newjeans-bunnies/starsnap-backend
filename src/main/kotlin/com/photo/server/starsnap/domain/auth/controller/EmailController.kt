package com.photo.server.starsnap.domain.auth.controller

import com.photo.server.starsnap.domain.auth.dto.EmailDto
import com.photo.server.starsnap.domain.auth.dto.EmailResponseDto
import com.photo.server.starsnap.domain.auth.service.AuthService
import com.photo.server.starsnap.domain.auth.service.EmailService
import com.photo.server.starsnap.global.config.BucketConfig
import com.photo.server.starsnap.global.dto.StatusDto
import com.photo.server.starsnap.global.error.exception.TooManyRequestException
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("api/auth/email")
class EmailController(
    private val emailService: EmailService,
    private val authService: AuthService,
    private val bucketConfig: BucketConfig
) {
    @PostMapping("/send")
    fun send(
        @RequestParam("email") @Pattern(
            regexp = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$", message = "only email"
        ) @Valid email: String
    ): StatusDto {
        if (!bucketConfig.emailSendBucket().tryConsume(1)) throw TooManyRequestException
        authService.checkValidEmail(email)
        return emailService.send(email)
    }

    @PostMapping("/verify")
    fun verify(@RequestBody @Valid emailDto: EmailDto): EmailResponseDto {
        val emailResponseDto = emailService.verify(emailDto)

        return emailResponseDto
    }

}