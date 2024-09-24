package com.photo.server.starsnap.domain.user.controller

import com.photo.server.starsnap.domain.user.controller.dto.UserDto
import com.photo.server.starsnap.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/user")
@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUserData(@RequestParam("user-id") userId: String): UserDto {
        val userData = userService.getUserData(userId)
        return userData
    }

}