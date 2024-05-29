package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.Bus
import com.alad1nks.dubovozki.model.BusSchedule
import com.alad1nks.dubovozki.repository.BusScheduleRepository
import org.springframework.stereotype.Service

private const val moscow = "msk"
private const val dubki = "dbk"

@Service
class BusScheduleService(
    val busScheduleRepository: BusScheduleRepository
) {

    fun getBusSchedule(): BusSchedule {
        val toMoscow = busScheduleRepository.findByDirection(moscow)
        val toDubki = busScheduleRepository.findByDirection(dubki)

        return BusSchedule(
            toMoscow = toMoscow,
            toDubki = toDubki
        )
    }

    fun updateBusSchedule(busSchedule: List<Bus>): List<Bus> {
        busScheduleRepository.deleteAllBuses()
        busScheduleRepository.saveAll(busSchedule)
        return busSchedule
    }
}
