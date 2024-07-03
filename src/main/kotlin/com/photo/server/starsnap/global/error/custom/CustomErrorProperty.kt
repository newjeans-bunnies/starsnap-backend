package com.photo.server.starsnap.global.error.custom

interface CustomErrorProperty {
    fun status(): Int
    fun message(): String
}