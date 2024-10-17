package com.photo.server.starsnap.global.config

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisCacheConfig {

    @Bean
    fun googleOIDCCacheManager(redisCF: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer()
                )
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer()
                )
            )
            // TTL 일주일로 설정
            .entryTtl(Duration.ofDays(7L))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisCF)
            .cacheDefaults(redisCacheConfiguration).build()
    }

    @Bean
    fun appleOIDCCacheManager(redisCF: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    StringRedisSerializer()
                )
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer()
                )
            )
            // TTL 일주일로 설정
            .entryTtl(Duration.ofDays(7L))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisCF)
            .cacheDefaults(redisCacheConfiguration).build()
    }
}