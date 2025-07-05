package com.photo.server.starsnap.usecase.auth.usecase

import com.photo.server.starsnap.usecase.auth.dto.Oauth2LoginDto
import com.photo.server.starsnap.usecase.auth.dto.Oauth2SignupDto
import com.photo.server.starsnap.usecase.auth.dto.TokenDto

interface Oauth2UseCase {
    fun login(loginDto: Oauth2LoginDto): TokenDto

    fun signup(signupDto: Oauth2SignupDto)

}