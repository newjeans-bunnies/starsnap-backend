package com.photo.server.starsnap.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}") private val redisHost: String,
    @Value("\${spring.data.redis.port}") private val redisPort: Int,
    @Value("\${spring.data.redis.password}") private val password: String
) {

    @Bean
    @Primary
    fun refreshTokenConnectionFactory(): RedisConnectionFactory {
        return redisConnectionFactory(0)
    }

    @Bean
    fun refreshTokenRedisTemplate(refreshTokenConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(refreshTokenConnectionFactory)
    }



    @Bean
    fun emailVerifyCodeConnectionFactory(): RedisConnectionFactory {
        return redisConnectionFactory(1)
    }

    @Bean
    fun emailVerifyCodeRedisTemplate(emailVerifyCodeConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(emailVerifyCodeConnectionFactory)
    }




    @Bean
    fun emailTokenConnectionFactory(): RedisConnectionFactory {
        return redisConnectionFactory(2)
    }

    @Bean
    fun emailTokenRedisTemplate(emailTokenConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        return StringRedisTemplate(emailTokenConnectionFactory)
    }





    @Bean
    fun googleOIDCOpenKeysConnectionFactory(): RedisConnectionFactory {
        return redisConnectionFactory(3)
    }

    @Bean
    fun googleOIDCOpenKeysRedisTemplate(
        googleOIDCOpenKeysConnectionFactory: RedisConnectionFactory
    ): StringRedisTemplate {
        return StringRedisTemplate(googleOIDCOpenKeysConnectionFactory)
    }




    @Bean(name = ["appleOIDCOpenKeysConnectionFactory"])
    @Qualifier("appleOIDCOpenKeysConnectionFactory")
    fun appleOIDCOpenKeysConnectionFactory(): RedisConnectionFactory {
        return redisConnectionFactory(4)
    }

    @Bean(name = ["appleOIDCOpenKeysRedisTemplate"])
    @Qualifier("appleOIDCOpenKeysRedisTemplate")
    fun appleOIDCOpenKeysRedisTemplate(
        appleOIDCOpenKeysConnectionFactory: RedisConnectionFactory
    ): StringRedisTemplate {
        return StringRedisTemplate(appleOIDCOpenKeysConnectionFactory)
    }




    fun redisConnectionFactory(dbIndex: Int): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost
        redisStandaloneConfiguration.port = redisPort
        redisStandaloneConfiguration.password = RedisPassword.of(password)
        redisStandaloneConfiguration.database = dbIndex

        val lettuceConnectionFactory = LettuceConnectionFactory(redisStandaloneConfiguration)
        return lettuceConnectionFactory
    }
}