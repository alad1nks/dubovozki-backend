package com.alad1nks.dubovozki.model

data class EmailVerificationCodeBody(
    val email: String,
    val code: String,
    val telegramId: String? = null
)
