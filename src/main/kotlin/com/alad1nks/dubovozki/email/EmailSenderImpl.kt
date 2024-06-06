package com.alad1nks.dubovozki.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderImpl : EmailSender {
    @Autowired
    lateinit var emailSender: JavaMailSender

    override fun sendEmail(address: String, subject: String, message: String) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.setTo(address)
        simpleMailMessage.subject = subject
        simpleMailMessage.text = message
        emailSender.send(simpleMailMessage)
    }
}
