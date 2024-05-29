package com.alad1nks.dubovozki.model

import jakarta.persistence.*

@Entity
data class Bus(
    @Id
    val id: Int,
    val dayOfWeek: Int,
    val dayTime: Long,
    val dayTimeString: String,
    val direction: String,
    val station: String
)
