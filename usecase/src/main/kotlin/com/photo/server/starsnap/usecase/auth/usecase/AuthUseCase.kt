package com.photo.server.starsnap.usecase.auth.usecase

import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.usecase.auth.dto.LoginDto
import com.photo.server.starsnap.usecase.auth.dto.SignupDto
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.user.dto.ChangePasswordDto


interface AuthUseCase {
    fun login(username: String, password: String): TokenDto

    fun signup(signupDto: SignupDto)
    
    fun deleteUser(user: User)
    
    fun changePassword(changePasswordDto: ChangePasswordDto): StatusDto

    fun setPassword(password: String, user: User)

    fun userRollback(loginDto: LoginDto): TokenDto

    fun checkValidUsername(username: String)

    fun checkValidEmail(email: String)

    fun matchesPassword(password: String, sparePassword: String)
}