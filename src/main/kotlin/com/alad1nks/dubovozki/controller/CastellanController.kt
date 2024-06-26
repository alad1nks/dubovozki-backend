package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.ServiceSchedule
import com.alad1nks.dubovozki.service.CastellanService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/castellan")
class CastellanController(
    val castellanService: CastellanService
) {

    @GetMapping
    fun getCastellanSchedule(): ServiceSchedule {
        return castellanService.getCastellanSchedule()
    }

    @PostMapping("/update")
    fun updateCastellanSchedule(@RequestBody schedule: ServiceSchedule): ServiceSchedule? {
        return castellanService.updateCastellanSchedule(schedule)
    }
}
