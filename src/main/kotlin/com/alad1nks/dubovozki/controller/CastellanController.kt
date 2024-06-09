package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.CastellanSchedule
import com.alad1nks.dubovozki.service.CastellanService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/castellan")
class CastellanController(
    val castellanService: CastellanService
) {

    @GetMapping
    fun getCastellanSchedule(): CastellanSchedule {
        return castellanService.getCastellanSchedule()
    }

    @PostMapping("/update")
    fun updateCastellanSchedule(@RequestBody update: CastellanSchedule): CastellanSchedule? {
        return castellanService.updateCastellanSchedule(update)
    }
}
