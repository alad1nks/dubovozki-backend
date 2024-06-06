package com.alad1nks.dubovozki

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DubovozkiBackendApplication

fun main(args: Array<String>) {
    runApplication<DubovozkiBackendApplication>(*args)
}
