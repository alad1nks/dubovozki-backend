package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.CastellanSchedule
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

private const val CASTELLAN_SCHEDULE = "CASTELLAN_SCHEDULE"

@Service
class CastellanService(
    private val redisTemplate: RedisTemplate<String, CastellanSchedule>
) {

    fun getCastellanSchedule(): CastellanSchedule {
        val valueOperations = redisTemplate.opsForValue()
        val castellanSchedule = valueOperations.get(CASTELLAN_SCHEDULE)

        return castellanSchedule ?: CastellanSchedule()
    }

    fun updateCastellanSchedule(castellanSchedule: CastellanSchedule): CastellanSchedule? {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(CASTELLAN_SCHEDULE, castellanSchedule)

        return valueOperations.get(CASTELLAN_SCHEDULE)
    }
}
