package com.blach.unilife.ui.data

data class RegistrationUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",

    val usernameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,

    val isFormValid: Boolean = false
)