package com.photo.server.starsnap.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional

@EnableScheduling
@Configuration
class SchedulingConfig(
//    private val userRepository: UserRepository,
//    private val snapRepository: SnapRepository,
) {
    var logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    @Async
    fun secessionUser() {
//        val sevenDaysAgo = LocalDateTime.now().minusDays(7) // 7일 전 날짜 계산
//        userRepository.deleteOldUser(sevenDaysAgo)
    }

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    @Async
    fun deleteSnap() {
//        val sevenDaysAgo = LocalDateTime.now().minusDays(7) // 7일 전 날짜 계산
//        snapRepository.deleteOldSnaps(sevenDaysAgo)
    }
}