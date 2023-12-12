package com.blach.unilife.data

data class RegistrationUIState(
    var username: String = "",
    var email: String = "",
    var password: String = "",

    var usernameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,

    var isFormValid: Boolean = false
)
