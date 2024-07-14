package com.sat.common.config.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.RedisSerializer
import java.time.Duration


@EnableCaching
@Configuration(proxyBeanMethods = false)
class CacheConfig {

    @Bean
    fun cacheManager(
        factory: RedisConnectionFactory,
        keySerializer: RedisSerializer<String>,
        valueSerializer: GenericJackson2JsonRedisSerializer,
    ): CacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(SerializationPair.fromSerializer(keySerializer))
            .serializeValuesWith(SerializationPair.fromSerializer(valueSerializer))
            .entryTtl(Duration.ofMinutes(1L))

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(factory)
            .cacheDefaults(configuration)
            .build()
    }
}
