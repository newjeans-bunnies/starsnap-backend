package com.photo.server.starsnap.adapter_usecase.auth.usecase

import com.photo.server.starsnap.adapter_infrastructure.auth.entity.RefreshTokenEntity
import com.photo.server.starsnap.adapter_infrastructure.auth.repository.RefreshTokenRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.global.security.jwt.JwtProvider
import com.photo.server.starsnap.adapter_infrastructure.user.repository.UserRepositoryImpl
import com.photo.server.starsnap.domain.auth.entity.RefreshToken
import com.photo.server.starsnap.domain.user.entity.User
import com.photo.server.starsnap.domain.user.type.Authority
import com.photo.server.starsnap.exception.auth.error.exception.AccountNotSuspendedException
import com.photo.server.starsnap.exception.auth.error.exception.ExistEmailException
import com.photo.server.starsnap.exception.auth.error.exception.InvalidPasswordException
import com.photo.server.starsnap.exception.auth.error.exception.PasswordAlreadySetException
import com.photo.server.starsnap.exception.auth.error.exception.UnsupportedLoginException
import com.photo.server.starsnap.exception.auth.error.exception.UnsupportedUserRollbackException
import com.photo.server.starsnap.exception.global.error.exception.ExistUsernameException
import com.photo.server.starsnap.exception.global.error.exception.InvalidRoleException
import com.photo.server.starsnap.exception.global.error.exception.NotExistUserIdException
import com.photo.server.starsnap.usecase.auth.dto.LoginDto
import com.photo.server.starsnap.usecase.auth.dto.SignupDto
import com.photo.server.starsnap.usecase.auth.dto.TokenDto
import com.photo.server.starsnap.usecase.auth.usecase.AuthUseCase
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import com.photo.server.starsnap.usecase.user.dto.ChangePasswordDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import io.viascom.nanoid.NanoId
import java.time.LocalDateTime

@Service
class AuthUseCaseImpl(
    private val userReportRepositoryImpl: UserRepositoryImpl,
    private val refreshTokenRepositoryImpl: RefreshTokenRepositoryImpl,
    private val emailUseCaseImpl: EmailCaseImpl,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : AuthUseCase {
    override fun login(username: String, password: String): TokenDto {
        val userData = userReportRepositoryImpl.findByUsername(username) ?: throw NotExistUserIdException

        if (userData.password.isNullOrEmpty()) throw UnsupportedLoginException
        matchesPassword(password, userData.password ?: "")

        val tokenDto = jwtProvider.receiveToken(userData.id, userData.authority)
        val refreshTokenEntity = RefreshTokenEntity(
            token = tokenDto.refreshToken, id = userData.id
        )

        refreshTokenRepositoryImpl.save(refreshTokenEntity.toDomain())

        return tokenDto
    }

    override fun signup(signupDto: SignupDto) {

        checkValidEmail(signupDto.email) // 메일 중복 체크
        checkValidUsername(signupDto.username) // 이름 중복 체크

        // 회원가입 토큰 확인
        emailUseCaseImpl.checkValidVerifyCode(signupDto.token, signupDto.email)

        // 유저 생성
        val userData = User(
            id = NanoId.generate(16),
            username = signupDto.username,
            password = passwordEncoder.encode(signupDto.password),
            email = signupDto.email,
            authority = Authority.USER,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        )

        // 유저 저장
        userReportRepositoryImpl.save(userData)
    }

    override fun deleteUser(user: User) {
        user.state = false

        userReportRepositoryImpl.save(user)

        val refreshToken = refreshTokenRepositoryImpl.findByIdOrNull(user.id)
        if (refreshToken != null) refreshTokenRepositoryImpl.delete(refreshToken)
    }

    override fun changePassword(changePasswordDto: ChangePasswordDto): StatusDto {
        val userData =
            userReportRepositoryImpl.findByUsername(changePasswordDto.username) ?: throw NotExistUserIdException
        matchesPassword(changePasswordDto.password, userData.password ?: throw InvalidRoleException)

        userData.password = passwordEncoder.encode(changePasswordDto.newPassword)

        userReportRepositoryImpl.save(userData)

        return StatusDto("OK", 200)
    }

    override fun setPassword(password: String, user: User) {
        if (user.password != null) throw PasswordAlreadySetException
        user.password = passwordEncoder.encode(password)
        userReportRepositoryImpl.save(user)
    }

    override fun userRollback(loginDto: LoginDto): TokenDto {
        val userData = userReportRepositoryImpl.findByUsername(loginDto.username) ?: throw NotExistUserIdException
        if (!userData.state) throw AccountNotSuspendedException
        matchesPassword(loginDto.password, userData.password ?: throw UnsupportedUserRollbackException)
        val tokenDto = jwtProvider.receiveToken(userData.id, userData.authority)

        val refreshToken = RefreshToken(
            token = tokenDto.refreshToken, id = userData.id
        )

        refreshTokenRepositoryImpl.save(refreshToken)

        return tokenDto
    }

    override fun checkValidUsername(username: String) {
        if (userReportRepositoryImpl.existsByUsername(username)) throw ExistUsernameException
    }

    override fun checkValidEmail(email: String) {
        if (userReportRepositoryImpl.existsByEmail(email)) throw ExistEmailException
    }

    override fun matchesPassword(password: String, sparePassword: String) {
        if (!passwordEncoder.matches(password, sparePassword)) throw InvalidPasswordException
    }
}