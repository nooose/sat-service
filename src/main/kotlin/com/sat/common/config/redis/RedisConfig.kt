package com.sat.common.config.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@EnableRedisRepositories
@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory,
        springSessionDefaultRedisSerializer: RedisSerializer<Any>,
    ): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory
            keySerializer = keySerializer()
            valueSerializer = genericJackson2JsonRedisSerializer()
            hashKeySerializer = keySerializer()
            hashValueSerializer = genericJackson2JsonRedisSerializer()
        }
    }

    @Bean
    fun keySerializer(): RedisSerializer<String> {
        return StringRedisSerializer()
    }

    @Bean
    fun genericJackson2JsonRedisSerializer(): GenericJackson2JsonRedisSerializer {
        return GenericJackson2JsonRedisSerializer()
    }
}
