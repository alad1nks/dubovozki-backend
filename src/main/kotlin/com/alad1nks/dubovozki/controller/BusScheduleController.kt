package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.Bus
import com.alad1nks.dubovozki.model.BusSchedule
import com.alad1nks.dubovozki.model.Revision
import com.alad1nks.dubovozki.service.BusScheduleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/bus-schedule")
class BusScheduleController(
    val busScheduleService: BusScheduleService
) {
    @GetMapping("/list")
    fun getBusSchedule(): BusSchedule = busScheduleService.getBusSchedule()

    @GetMapping("/revision")
    fun getBusScheduleRevision(): Revision = busScheduleService.getBusScheduleRevision()

    @PostMapping("/update")
    fun updateBusSchedule(
        @RequestBody busList: List<Bus>
    ): List<Bus> = busScheduleService.updateBusSchedule(busList)
}
