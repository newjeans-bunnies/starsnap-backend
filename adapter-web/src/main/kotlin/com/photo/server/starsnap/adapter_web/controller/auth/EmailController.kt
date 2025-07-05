package com.photo.server.starsnap.adapter_web.controller.auth

import com.photo.server.starsnap.adapter_usecase.auth.usecase.AuthUseCaseImpl
import com.photo.server.starsnap.adapter_usecase.auth.usecase.EmailCaseImpl
import com.photo.server.starsnap.config.BucketConfig
import com.photo.server.starsnap.usecase.auth.dto.EmailDto
import com.photo.server.starsnap.usecase.auth.dto.EmailResponseDto
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("api/auth/email")
class EmailController(
    private val emailUseCaseImpl: EmailCaseImpl,
    private val authUseCaseImpl: AuthUseCaseImpl,
    private val bucketConfig: BucketConfig
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send")
    fun send(
        @RequestParam("email") @Pattern(
            regexp = "^[a-zA-Z0-9+-_.]+@[0-9a-zA-Z]+\\.[a-zA-Z]{2,3}\$", message = "only email"
        ) @Valid email: String
    ): StatusDto {
        // if (!bucketConfig.emailSendBucket().tryConsume(1)) throw TooManyRequestException
        authUseCaseImpl.checkValidEmail(email)
        return emailUseCaseImpl.send(email)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verify")
    fun verify(@RequestBody @Valid emailDto: EmailDto): EmailResponseDto {
        val emailResponseDto = emailUseCaseImpl.verify(emailDto)

        return emailResponseDto
    }

}