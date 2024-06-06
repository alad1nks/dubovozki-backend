package com.alad1nks.dubovozki.email

import com.alad1nks.dubovozki.model.EmailValidationStatus
import com.alad1nks.dubovozki.model.EmailValidationStatus.*
import org.apache.commons.validator.routines.EmailValidator

object HseEmailValidator {
    inline val String.validationStatus: EmailValidationStatus get() {
        if (isNotEmail()) {
            return NOT_EMAIL
        }

        if (isNotHseEmail()) {
            return INVALID_DOMAIN
        }

        return VALID
    }


    fun String.isEmail(): Boolean {
        return EmailValidator.getInstance().isValid(this)
    }

    fun String.isNotEmail(): Boolean {
        return !isEmail()
    }

    fun String.isHseEmail(): Boolean {
        return endsWith("@edu.hse.ru") || endsWith("@hse.ru")
    }

    fun String.isNotHseEmail(): Boolean {
        return !isHseEmail()
    }
}
