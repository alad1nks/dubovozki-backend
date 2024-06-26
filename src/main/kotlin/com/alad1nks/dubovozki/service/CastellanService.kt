package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.ServiceSchedule
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

private const val CASTELLAN_SCHEDULE = "CASTELLAN_SCHEDULE"

@Service
class CastellanService(
    private val redisTemplate: RedisTemplate<String, ServiceSchedule>
) {

    fun getCastellanSchedule(): ServiceSchedule {
        val valueOperations = redisTemplate.opsForValue()
        val castellanSchedule = valueOperations.get(CASTELLAN_SCHEDULE)

        return castellanSchedule ?: ServiceSchedule()
    }

    fun updateCastellanSchedule(serviceSchedule: ServiceSchedule): ServiceSchedule? {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(CASTELLAN_SCHEDULE, serviceSchedule)

        return valueOperations.get(CASTELLAN_SCHEDULE)
    }
}
