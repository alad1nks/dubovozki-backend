package com.alad1nks.dubovozki.service

import com.alad1nks.dubovozki.email.CodeGenerator
import com.alad1nks.dubovozki.email.EmailSender
import com.alad1nks.dubovozki.email.HseEmailValidator.validationStatus
import com.alad1nks.dubovozki.model.*
import com.alad1nks.dubovozki.model.EmailValidationStatus.VALID
import com.alad1nks.dubovozki.repository.UserRepository
import com.alad1nks.dubovozki.security.JwtTokenUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

private const val EXPIRATION_TIME_MS: Long = 5 * 60 * 1000
private const val EMAIL_VERIFICATION_CODE = "EMAIL_VERIFICATION_CODE"

@Service
class RegistrationService(
    val userRepository: UserRepository,
    val emailSender: EmailSender,
    val objectMapper: ObjectMapper,
    val jwtUtil: JwtTokenUtil,
    redisTemplate: RedisTemplate<String, Any>
) {

    private val hashOperations = redisTemplate.opsForHash<String, EmailVerificationCode>()

    fun validateEmail(email: String): EmailValidationStatus {
        val emailValidationStatus: EmailValidationStatus = email.validationStatus

        if (emailValidationStatus == VALID) {
            checkStatusAndSendCode(email)
        }

        return emailValidationStatus
    }

    fun verifyEmail(emailVerificationCodeBody: EmailVerificationCodeBody): EmailVerificationCodeResponse {
        val email = emailVerificationCodeBody.email
        val code = emailVerificationCodeBody.code
        val jwt = buildJwt(email, code)

        return EmailVerificationCodeResponse(jwt)
    }

    fun getRegistrationStatus(): Map<String, EmailVerificationCode> {
        val entries = getEntries()
        val registrationStatus: Map<String, EmailVerificationCode> = objectMapper.convertValue(entries)

        return registrationStatus
    }

    @Scheduled(fixedRate = 60000)
    fun deleteExpiredEmailVerificationCodes() {
        val registrationStatus = getRegistrationStatus()
        val now = System.currentTimeMillis()
        registrationStatus.values.forEach {
            if (it.expiryDate < now) hashOperations.delete(EMAIL_VERIFICATION_CODE, it.email)
        }
    }

    private fun checkStatusAndSendCode(email: String) {
        if (email.isExpired()) {
            val code = CodeGenerator.verificationCode()
            val now = System.currentTimeMillis()
            val expiryDate = now + EXPIRATION_TIME_MS
            val emailVerificationCode = EmailVerificationCode(email, code, expiryDate)
            emailSender.sendEmail(email, "Код активации", code)
            hashOperations.put(EMAIL_VERIFICATION_CODE, email, emailVerificationCode)

            return
        }

        throw RuntimeException("The code is not expired")
    }

    private fun buildJwt(email: String, code: String): String? {
        val registrationStatus = getRegistrationStatus()
        val emailVerificationCode = registrationStatus[email] ?: return null

        if (emailVerificationCode.attempts >= 3) return null

        if (emailVerificationCode.code != code) {
            incrementAttempts(email)
            return null
        }

        removeEmailVerificationCode(email)

        val jwt = jwtUtil.generateToken(email, UserRole.USER.name)
        val user = User(email)

        if (!userRepository.existsByEmail(email)) userRepository.save(user)

        return jwt
    }

    private fun getEntries(): Map<String, EmailVerificationCode> {
        return hashOperations.entries(EMAIL_VERIFICATION_CODE).toMap()
    }

    private fun String.isExpired(): Boolean {
        val registrationStatus = getRegistrationStatus()

        return registrationStatus[this]?.let {
            it.expiryDate < Calendar.getInstance().timeInMillis
        } ?: true
    }

    private fun incrementAttempts(email: String) {
        val registrationStatus = getRegistrationStatus()
        val emailVerificationCode = registrationStatus[email] ?: return
        val attempts = emailVerificationCode.attempts + 1
        hashOperations.put(EMAIL_VERIFICATION_CODE, email, emailVerificationCode.copy(attempts = attempts))
    }

    private fun removeEmailVerificationCode(email: String) {
        hashOperations.delete(EMAIL_VERIFICATION_CODE, email)
    }
}
