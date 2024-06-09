package com.alad1nks.dubovozki.model

sealed interface RandomCoffeeMyStatus {

    data class Match(
        val word: String,
        val partner: String
    ) : RandomCoffeeMyStatus

    data class Searching(
        val isSearching: Boolean
    ) : RandomCoffeeMyStatus
}
