package com.photo.server.starsnap.exception.global.error.custom

interface CustomErrorProperty {
    fun status(): Int
    fun message(): String
}