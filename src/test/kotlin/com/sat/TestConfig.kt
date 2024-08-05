package com.sat

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.ninjasquad.springmockk.MockkBean
import com.sat.common.config.redis.RedisConfig
import com.sat.user.command.application.PointCacheInitializer
import io.mockk.impl.annotations.RelaxedMockK
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer

@Import(value = [
    KotlinJdslAutoConfiguration::class,
])
@EnableAutoConfiguration(exclude = [
    RedisAutoConfiguration::class
])
@TestConfiguration
class TestConfig {

    @RelaxedMockK
    lateinit var redisConfig: RedisConfig
    @RelaxedMockK
    lateinit var redisSerializer: RedisSerializer<String>
    @RelaxedMockK
    lateinit var genericJackson2JsonRedisSerializer: GenericJackson2JsonRedisSerializer
    @MockkBean
    lateinit var redisTemplate: RedisTemplate<String, Any>
    @MockkBean
    lateinit var connectionFactory: RedisConnectionFactory
    @RelaxedMockK
    lateinit var pointCacheInitializer: PointCacheInitializer
}
