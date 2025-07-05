package com.photo.server.starsnap.usecase.star.usecase

interface FanUseCase {
    fun joinFan(userId: String, starId: String)

    fun disconnectFan(userId: String, starId: String)
}