package com.alad1nks.dubovozki.email

import java.security.SecureRandom

object CodeGenerator {
    private val random: SecureRandom = SecureRandom()

    fun verificationCode(): String {
        val sb = StringBuilder()
        for (i in 0 until 6) {
            sb.append(random.nextInt(10))
        }
        return sb.toString()
    }
}
