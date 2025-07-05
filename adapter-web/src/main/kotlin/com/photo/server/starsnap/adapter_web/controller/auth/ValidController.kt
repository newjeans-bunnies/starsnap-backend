package com.photo.server.starsnap.adapter_web.controller.auth

import com.photo.server.starsnap.adapter_usecase.auth.usecase.ValidUseCaseImpl
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/valid")
class ValidController(
    private val validUseCaseImpl: ValidUseCaseImpl
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username")
    fun validUsername(@RequestParam("username") username: String): StatusDto {
        return validUseCaseImpl.validUsername(username)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email")
    fun validEmail(@RequestParam("email") email: String): StatusDto {
        return validUseCaseImpl.validEmail(email)
    }
}