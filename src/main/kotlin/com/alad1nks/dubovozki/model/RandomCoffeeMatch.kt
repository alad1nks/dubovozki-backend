package com.alad1nks.dubovozki.model

data class RandomCoffeeMatch(
    val word: String,
    val user1: EmailAndTelegramUsername,
    val user2: EmailAndTelegramUsername
)
