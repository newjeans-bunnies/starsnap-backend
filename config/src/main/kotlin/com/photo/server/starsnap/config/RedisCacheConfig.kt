package com.photo.server.starsnap.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisCacheConfig {

    @Bean(name = ["googleOIDCCacheManager"])
    @Primary
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

    @Bean(name = ["appleOIDCCacheManager"])
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