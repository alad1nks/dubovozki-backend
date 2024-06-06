package com.alad1nks.dubovozki.email

interface EmailSender {
    fun sendEmail(address: String, subject: String, message: String)
}
