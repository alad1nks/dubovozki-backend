package com.alad1nks.dubovozki.model

data class ServiceSchedule(
    val firstBuilding: List<ServiceDay> = emptyList(),
    val secondBuilding: List<ServiceDay> = emptyList(),
    val thirdBuilding: List<ServiceDay> = emptyList()
)
