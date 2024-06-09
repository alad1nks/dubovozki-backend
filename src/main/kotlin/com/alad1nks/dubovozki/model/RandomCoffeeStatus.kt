package com.alad1nks.dubovozki.model

data class RandomCoffeeStatus(
    val unusedWords: List<String>,
    val searchingUsers: List<EmailAndTelegramUsername>,
    val matches: List<RandomCoffeeMatch>
)
