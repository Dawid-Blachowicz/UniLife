package com.blach.unilife.view.utils

object Validator {

    fun validateUsername(username: String): ValidationResult {
        return ValidationResult(
            (username.isNotEmpty()
                    && username.length >= 3
                    && username.length <= 20)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        val emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        return ValidationResult(
            (!email.isNullOrEmpty()
                    && email.matches(emailRegex))
        )
    }

    fun validatePassword(password: String): ValidationResult {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&#])[A-Za-z\\d@\$!%*?&#]{8,}$".toRegex()
        return ValidationResult(
            (!password.isNullOrEmpty()
                    && password.length >= 8
                    && password.matches(passwordRegex))
        )
    }

    fun validateRegistrationForm(usernameResult: ValidationResult, emailResult: ValidationResult, passwordResult: ValidationResult): ValidationResult {
        return ValidationResult(
            usernameResult.status
                    && emailResult.status
                    && passwordResult.status
        )
    }
}
data class ValidationResult(
    val status: Boolean = false
)