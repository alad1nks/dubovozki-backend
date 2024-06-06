package com.alad1nks.dubovozki.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    val email: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
)
