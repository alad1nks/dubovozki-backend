package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.model.Bus
import com.alad1nks.dubovozki.model.BusSchedule
import com.alad1nks.dubovozki.model.Revision
import com.alad1nks.dubovozki.repository.BusScheduleRepository
import com.alad1nks.dubovozki.storage.Storage
import org.springframework.stereotype.Service

private const val moscow = "msk"
private const val dubki = "dbk"

@Service
class BusScheduleService(
    val busScheduleRepository: BusScheduleRepository,
    val storage: Storage
) {
    fun getBusSchedule(): BusSchedule {
        val toMoscow = busScheduleRepository.findByDirection(moscow)
        val toDubki = busScheduleRepository.findByDirection(dubki)

        return BusSchedule(
            toMoscow = toMoscow,
            toDubki = toDubki
        )
    }

    fun getBusScheduleRevision(): Revision = Revision(storage.getBusScheduleRevision())

    fun updateBusSchedule(busSchedule: List<Bus>): List<Bus> {
        storage.updateBusScheduleRevision()
        busScheduleRepository.deleteAllBuses()
        busScheduleRepository.saveAll(busSchedule)
        return busSchedule
    }
}
