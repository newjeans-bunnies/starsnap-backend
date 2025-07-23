package com.photo.server.starsnap.adapter_usecase.scheduler

import com.photo.server.starsnap.adapter_usecase.file.usecase.FileUseCaseImpl
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PhotoCleanupScheduler(
    private val fileUseCaseImpl: FileUseCaseImpl
) {
    // 1시간 마다 실행
    @Scheduled(cron = "0 0 * * * *")
    fun expirePhoto() {
        fileUseCaseImpl.expirePhoto()
    }

}