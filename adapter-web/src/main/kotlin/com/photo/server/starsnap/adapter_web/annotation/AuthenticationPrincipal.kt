package com.photo.server.starsnap.adapter_web.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

// 유저 아이디 가져오기
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? -1 : #this.userId")
annotation class AuthenticationPrincipalId

// 유저 정보 가져오기
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? -1 : #this.user")
annotation class AuthenticationPrincipalUserData