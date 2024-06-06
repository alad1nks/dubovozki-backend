package com.alad1nks.dubovozki.model

import java.io.Serializable

data class EmailVerificationCode(
    val email: String,
    val code: String,
    val expiryDate: Long,
    val attempts: Int = 0
): Serializable
