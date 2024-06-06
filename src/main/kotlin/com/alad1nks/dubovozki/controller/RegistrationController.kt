package com.alad1nks.dubovozki.controller

import com.alad1nks.dubovozki.model.*
import com.alad1nks.dubovozki.service.RegistrationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/router/registration")
class RegistrationController(
    val registrationService: RegistrationService
) {

    @PostMapping("/email/validate")
    fun validateEmail(@RequestBody email: Email): EmailValidationStatus =
        registrationService.validateEmail(email.email)

    @PostMapping("/email/verify")
    fun verifyEmail(@RequestBody email: EmailVerificationCodeBody): EmailVerificationCodeResponse =
        registrationService.verifyEmail(email)

    @GetMapping("/status")
    fun getRegistrationStatus(): Map<String, EmailVerificationCode> = registrationService.getRegistrationStatus()
}
