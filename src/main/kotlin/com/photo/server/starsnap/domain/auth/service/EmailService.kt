package com.photo.server.starsnap.domain.auth.service

import com.photo.server.starsnap.domain.auth.EmailTokenEntity
import com.photo.server.starsnap.domain.auth.EmailVerifyEntity
import com.photo.server.starsnap.domain.auth.controller.dto.EmailDto
import com.photo.server.starsnap.domain.auth.controller.dto.EmailResponseDto
import com.photo.server.starsnap.domain.auth.repository.EmailTokenRepository
import com.photo.server.starsnap.domain.auth.repository.EmailVerifyRepository
import com.photo.server.starsnap.global.dto.StatusDto
import io.viascom.nanoid.NanoId
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver


@Service
class EmailService(
    private val emailVerifyRepository: EmailVerifyRepository,
    private val emailTokenRepository: EmailTokenRepository,
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val senderEmail: String
) {

    // 인증 코드 전송
    fun send(email: String): StatusDto {

        val emailFrom = createEmailFrom(email)
        javaMailSender.send(emailFrom)

        return StatusDto("OK", 200)
    }

    // 인증 코드 확인
    fun verify(emailDto: EmailDto): EmailResponseDto {
        val verifyEmail =
            emailVerifyRepository.findByIdOrNull(emailDto.email) ?: throw RuntimeException("Email not found")
        if (verifyEmail.verifyCode != emailDto.verifyCode) {
            throw RuntimeException("Invalid verification code")
        }

        emailVerifyRepository.delete(verifyEmail)

        val emailTokenData = EmailTokenEntity(emailDto.email, createToken())
        emailTokenRepository.save(emailTokenData)

        return EmailResponseDto(emailTokenData.email, emailTokenData.token)
    }

    // 메일 토큰 확인
    fun checkValidVerifyCode(token: String, email: String) {
        val emailTokenEntity =
            emailTokenRepository.findByIdOrNull(email) ?: throw RuntimeException("Verification code not found")
        if (emailTokenEntity.token != token) throw RuntimeException("Invalid verification code")
    }

    // 토큰 삭제
    fun deleteToken(token: String, email: String) {
        val emailTokenEntity = emailTokenRepository.findByIdOrNull(email) ?: throw RuntimeException("Email not found")
        emailTokenRepository.delete(emailTokenEntity)
    }

    // 토큰 생성
    private fun createToken() = NanoId.generate(12)

    // 인증 코드 생성
    private fun createVerifyCode() = NanoId.generate(6, "1234567890")

    // 메일 폼 생성
    private fun createEmailFrom(email: String): MimeMessage {
        val verifyCode = createVerifyCode()

        val message: MimeMessage = javaMailSender.createMimeMessage()
        message.addRecipients(MimeMessage.RecipientType.TO, email)
        message.subject = "setSubject"
        message.setFrom(senderEmail)
        message.setText(setContext(verifyCode), "utf-8", "html")

        val emailVerifyData = EmailVerifyEntity(email, verifyCode)
        emailVerifyRepository.save(emailVerifyData)

        return message
    }

    // 메일 폼 생성
    private fun setContext(verifyCode: String): String {
        val context = Context()
        val templateEngine = TemplateEngine()
        val templateResolver = ClassLoaderTemplateResolver()

        context.setVariable("code", verifyCode)
        templateResolver.prefix = "templates/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.isCacheable = false

        templateEngine.setTemplateResolver(templateResolver)

        return templateEngine.process("emailSend.html", context)
    }
}