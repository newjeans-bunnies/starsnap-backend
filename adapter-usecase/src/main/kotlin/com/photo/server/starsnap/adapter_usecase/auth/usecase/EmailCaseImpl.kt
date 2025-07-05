package com.photo.server.starsnap.adapter_usecase.auth.usecase

import com.photo.server.starsnap.adapter_infrastructure.auth.repository.EmailTokenRepositoryImpl
import com.photo.server.starsnap.adapter_infrastructure.auth.repository.EmailVerifyRepositoryImpl
import com.photo.server.starsnap.domain.auth.entity.EmailToken
import com.photo.server.starsnap.domain.auth.entity.EmailVerify
import com.photo.server.starsnap.exception.auth.error.exception.InvalidVerificationCodeException
import com.photo.server.starsnap.exception.auth.error.exception.NotFoundEmailException
import com.photo.server.starsnap.usecase.auth.dto.EmailDto
import com.photo.server.starsnap.usecase.auth.dto.EmailResponseDto
import com.photo.server.starsnap.usecase.auth.usecase.EmailUserCase
import com.photo.server.starsnap.usecase.global.dto.StatusDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import io.viascom.nanoid.NanoId
import jakarta.mail.internet.MimeMessage

@Service
class EmailCaseImpl(
    private val emailVerifyRepositoryImpl: EmailVerifyRepositoryImpl,
    private val emailTokenRepositoryImpl: EmailTokenRepositoryImpl,
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val senderEmail: String
) : EmailUserCase {

    // 인증코드 전송
    override fun send(email: String): StatusDto {
        val emailFrom = createEmailFrom(email)
        javaMailSender.send(emailFrom)

        return StatusDto("OK", 200)
    }

    // 인증 코드 확인
    override fun verify(emailDto: EmailDto): EmailResponseDto {
        val verifyEmail =
            emailVerifyRepositoryImpl.findByIdOrNull(emailDto.email) ?: throw NotFoundEmailException
        if (verifyEmail.verifyCode != emailDto.verifyCode) {
            throw InvalidVerificationCodeException
        }

        emailVerifyRepositoryImpl.delete(verifyEmail)

        val emailTokenData = EmailToken(emailDto.email, createToken())
        emailTokenRepositoryImpl.save(emailTokenData)

        return EmailResponseDto(emailTokenData.email, emailTokenData.token)
    }

    // 메일 토큰 확인
    override fun checkValidVerifyCode(token: String, email: String) {
        val emailTokenEntity =
            emailTokenRepositoryImpl.findByIdOrNull(email) ?: throw NotFoundEmailException
        if (emailTokenEntity.token != token) throw InvalidVerificationCodeException

        deleteToken(token, email)

    }

    // 토큰 삭제
    override fun deleteToken(token: String, email: String) {
        val emailTokenEntity = emailTokenRepositoryImpl.findByIdOrNull(email) ?: throw NotFoundEmailException
        emailTokenRepositoryImpl.delete(emailTokenEntity)
    }

    // 메일 폼 생성
    override fun setContext(verifyCode: String): String {
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

    // 토큰 생성
    override fun createToken() = NanoId.generate(12)

    // 인증 코드 생성
    override fun createVerifyCode() = NanoId.generate(4, "1234567890")

    // 메일 폼 생성
    override fun createEmailFrom(email: String): MimeMessage {
        val verifyCode = createVerifyCode()

        val message: MimeMessage = javaMailSender.createMimeMessage()
        message.addRecipients(MimeMessage.RecipientType.TO, email)
        message.subject = "setSubject"
        message.setFrom(senderEmail)
        message.setText(setContext(verifyCode), "utf-8", "html")

        val emailVerifyData = EmailVerify(email, verifyCode)
        emailVerifyRepositoryImpl.save(emailVerifyData)

        return message
    }
}