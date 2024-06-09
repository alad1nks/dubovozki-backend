package com.alad1nks.dubovozki.redis

import com.alad1nks.dubovozki.model.ServiceSchedule
import com.alad1nks.dubovozki.model.EmailAndTelegramUsername
import com.alad1nks.dubovozki.model.RandomCoffeeMatch
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        return createRedisTemplate(connectionFactory)
    }

    @Bean
    fun redisTemplateEmailAndTelegramUsername(connectionFactory: RedisConnectionFactory): RedisTemplate<String, EmailAndTelegramUsername> {
        return createRedisTemplate(connectionFactory)
    }

    @Bean
    fun redisTemplateRandomCoffeeMatch(connectionFactory: RedisConnectionFactory): RedisTemplate<String, RandomCoffeeMatch> {
        return createRedisTemplate(connectionFactory)
    }

    @Bean
    fun redisTemplateCastellanSchedule(connectionFactory: RedisConnectionFactory): RedisTemplate<String, ServiceSchedule> {
        return createRedisTemplate(connectionFactory)
    }

    private fun <T> createRedisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, T> {
        val template = RedisTemplate<String, T>()

        val objectMapper = ObjectMapper()
        val module = SimpleModule()
        objectMapper.registerModule(module)
        val serializer = GenericJackson2JsonRedisSerializer(objectMapper)

        template.connectionFactory = connectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = serializer
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = serializer

        return template
    }
}
