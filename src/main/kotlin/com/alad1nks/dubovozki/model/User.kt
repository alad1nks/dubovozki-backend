package com.alad1nks.dubovozki.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val email: String
)
