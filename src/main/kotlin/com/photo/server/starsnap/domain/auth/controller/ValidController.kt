package com.photo.server.starsnap.domain.auth.controller

import com.photo.server.starsnap.domain.auth.service.ValidService
import com.photo.server.starsnap.global.dto.StatusDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/valid")
class ValidController(
    private val validService: ValidService
) {
    @GetMapping("/username")
    fun validUsername(@RequestParam("username") username: String): StatusDto {
        return validService.validUsername(username)
    }

    @GetMapping("/email")
    fun validEmail(@RequestParam("email") email: String): StatusDto {
        return validService.validEmail(email)
    }
}