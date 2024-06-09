package com.alad1nks.dubovozki.model

data class CastellanSchedule(
    val firstBuilding: List<CastellanDay> = emptyList(),
    val secondBuilding: List<CastellanDay> = emptyList(),
    val thirdBuilding: List<CastellanDay> = emptyList()
)
