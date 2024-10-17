package com.photo.server.starsnap.domain.auth.controller

import com.photo.server.starsnap.domain.auth.dto.TokenDto
import com.photo.server.starsnap.domain.auth.service.Oauth2Service
import com.photo.server.starsnap.domain.user.entity.UserEntity
import com.photo.server.starsnap.global.annotation.AuthenticationPrincipalUserData
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/oauth")
class Oauth2Controller(
    private val oauth2Service: Oauth2Service
) {
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestParam("id-token") idToken: String, @RequestParam("type") type: String): TokenDto {
        return oauth2Service.login(idToken, type)
    }

    @PostMapping("/signup")
    fun signup(@RequestParam("id-token") idToken: String, @RequestParam type: String, @RequestParam username: String) {
        oauth2Service.signup(idToken, type, username)
    }


    @PatchMapping("/unconnected")
    fun unconnected(
        @RequestParam("id-token") idToken: String,
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("type") type: String
    ) {
        return oauth2Service.unconnected(idToken, user, type)
    }

    @PatchMapping("/connection")
    fun connection(
        @RequestParam("id-token") idToken: String,
        @AuthenticationPrincipalUserData user: UserEntity,
        @RequestParam("type") type: String
    ) {
        return oauth2Service.connection(idToken, user, type)
    }
}