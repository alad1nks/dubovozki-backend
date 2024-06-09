package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.ServiceSchedule
import com.alad1nks.dubovozki.service.GymService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/gym")
class GymController(
    val gymService: GymService
) {

    @GetMapping
    fun getCastellanSchedule(): ServiceSchedule {
        return gymService.getGymSchedule()
    }

    @PostMapping("/update")
    fun updateCastellanSchedule(@RequestBody schedule: ServiceSchedule): ServiceSchedule? {
        return gymService.updateGymSchedule(schedule)
    }
}
