package com.blach.unilife.data

sealed class RegistrationUIEvent{
    data class UsernameChanged(val username: String): RegistrationUIEvent()
    data class EmailChanged(val email: String): RegistrationUIEvent()
    data class PasswordChanged(val password: String): RegistrationUIEvent()

    object RegisterButtonClicked: RegistrationUIEvent()
}
