package com.photo.server.starsnap.config

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class BucketConfig {

    @Bean
    fun loginBucket(): Bucket {
        // 5초에 3번
        val refill = Refill.intervally(3, Duration.ofSeconds(5))

        //버킷의 크기는 3개
        val limit = Bandwidth.classic(3, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun signupBucket(): Bucket {
        // 15초에 5번 가능
        val refill = Refill.intervally(5, Duration.ofSeconds(15))

        //버킷의 크기는 5개
        val limit = Bandwidth.classic(5, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun createSnapBucket(): Bucket {
        // 2초에 한번 가능
        val refill = Refill.intervally(1, Duration.ofSeconds(2))

        // 버킷의 크기는 1
        val limit = Bandwidth.classic(1, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun emailSendBucket(): Bucket {
        // 10분에 5번 가능
        val refill = Refill.intervally(5, Duration.ofSeconds(10 * 60))

        // 버킷 크기는 5
        val limit = Bandwidth.classic(5, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun reissueTokenBucket(): Bucket {
        // 10분에 5번 가능
        val refill = Refill.intervally(5, Duration.ofSeconds(1 * 60))

        // 버킷 크기는 5
        val limit = Bandwidth.classic(5, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun deleteUserBucket(): Bucket {
        // 5분에 3번 가능
        val refill = Refill.intervally(3, Duration.ofSeconds(5 * 60))

        // 버킷 크기는 3
        val limit = Bandwidth.classic(3, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun followBucket(): Bucket {
        // 1초에 5번 가능
        val refill = Refill.intervally(5, Duration.ofSeconds(1))

        // 버킷 크기는 5
        val limit = Bandwidth.classic(5, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun changePasswordBucket(): Bucket {
        // 10분에 5번 가능
        val refill = Refill.intervally(3, Duration.ofSeconds(10))

        // 버킷 크기는 5
        val limit = Bandwidth.classic(3, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun reportBucket(): Bucket {
        // 10분에 5번 가능
        val refill = Refill.intervally(3, Duration.ofSeconds(10))

        // 버킷 크기는 5
        val limit = Bandwidth.classic(3, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun getReportBucket(): Bucket {
        // 3초에 2번 가능
        val refill = Refill.intervally(3, Duration.ofSeconds(2))

        val limit = Bandwidth.classic(3, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }

    @Bean
    fun getFollowData(): Bucket {
        // 3초에 2번 가능
        val refill = Refill.intervally(3, Duration.ofSeconds(2))

        val limit = Bandwidth.classic(3, refill)

        return Bucket.builder()
            .addLimit(limit)
            .build()
    }
}