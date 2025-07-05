package com.photo.server.starsnap.adapter_web.controller.user

import com.photo.server.starsnap.adapter_usecase.user.usecase.UserUseCaseImpl
import com.photo.server.starsnap.usecase.user.dto.UserDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/user")
@RestController
class UserController(
    private val userUseCaseImpl: UserUseCaseImpl
) {
    @GetMapping("/get")
    fun getUserData(@RequestParam("user-id") userId: String): UserDto {
        val userData = userUseCaseImpl.getUser(userId)
        return userData
    }

}