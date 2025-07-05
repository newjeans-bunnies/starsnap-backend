package com.photo.server.starsnap.exception.global.error.custom

abstract class CustomException(
    val errorProperty: CustomErrorProperty
) : RuntimeException() {
    override fun fillInStackTrace() = this
}