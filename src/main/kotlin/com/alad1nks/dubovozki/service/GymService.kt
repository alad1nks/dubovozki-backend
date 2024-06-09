package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.ServiceSchedule
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

private const val GYM_SCHEDULE = "GYM_SCHEDULE"

@Service
class GymService(
    private val redisTemplate: RedisTemplate<String, ServiceSchedule>
) {

    fun getGymSchedule(): ServiceSchedule {
        val valueOperations = redisTemplate.opsForValue()
        val castellanSchedule = valueOperations.get(GYM_SCHEDULE)

        return castellanSchedule ?: ServiceSchedule()
    }

    fun updateGymSchedule(serviceSchedule: ServiceSchedule): ServiceSchedule? {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(GYM_SCHEDULE, serviceSchedule)

        return valueOperations.get(GYM_SCHEDULE)
    }
}
