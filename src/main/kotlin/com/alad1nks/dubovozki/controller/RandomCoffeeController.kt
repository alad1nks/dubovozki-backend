package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.EmailAndTelegramUsername
import com.alad1nks.dubovozki.model.RandomCoffeeMyStatus
import com.alad1nks.dubovozki.model.RandomCoffeeStatus
import com.alad1nks.dubovozki.model.TelegramUsername
import com.alad1nks.dubovozki.service.RandomCoffeeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/random-coffee")
class RandomCoffeeController(
    val randomCoffeeService: RandomCoffeeService
) {

    @PostMapping("/add-words")
    fun addWords(@RequestBody words: List<String>): List<String> = randomCoffeeService.addWords(words)

    @PostMapping("/generate-pairs")
    fun generatePairs(): RandomCoffeeStatus = randomCoffeeService.generatePairs()

    @PostMapping("/join")
    fun join(@RequestBody telegramUsername: TelegramUsername): EmailAndTelegramUsername =
        randomCoffeeService.join(telegramUsername)

    @GetMapping("/my-match")
    fun getMyStatus(): RandomCoffeeMyStatus = randomCoffeeService.getMyStatus()

    @GetMapping("/status")
    fun getStatus(): RandomCoffeeStatus = randomCoffeeService.getStatus()
}
