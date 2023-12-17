package com.blach.unilife.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.ui.data.RegistrationUIEvent
import com.blach.unilife.ui.data.RegistrationUIState
import com.blach.unilife.model.repository.UserRepository
import com.blach.unilife.ui.navigation.NavigationCommand
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.ui.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val TAG = RegistrationViewModel::class.simpleName

    private val _registrationUIState = MutableStateFlow(RegistrationUIState())
    val registrationUIState = _registrationUIState.asStateFlow()

    private val _navigationCommand = MutableSharedFlow<NavigationCommand>()
    val navigationCommand = _navigationCommand.asSharedFlow()

    private val _registerResult = MutableSharedFlow<Result<Boolean>>()
    val registerResult = _registerResult.asSharedFlow()

    fun onEvent(event: RegistrationUIEvent) {
        validateDataWithRules()
        when(event) {
            is RegistrationUIEvent.UsernameChanged -> {
                _registrationUIState.value = _registrationUIState.value.copy(
                    username = event.username
                )
            }

            is RegistrationUIEvent.EmailChanged -> {
                _registrationUIState.value = _registrationUIState.value.copy(
                    email = event.email
                )
            }

            is RegistrationUIEvent.PasswordChanged -> {
                _registrationUIState.value = _registrationUIState.value.copy(
                    password = event.password
                )
            }

            is RegistrationUIEvent.RegisterButtonClicked -> {
                register()
            }
        }
    }

    private fun validateDataWithRules() {
        val usernameResult = Validator.validateUsername(
            username = registrationUIState.value.username
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        val isFormValidResult = Validator.validateRegistrationForm(
            usernameResult = usernameResult,
            emailResult = emailResult,
            passwordResult = passwordResult
        )

        _registrationUIState.value = _registrationUIState.value.copy(
            usernameError = usernameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            isFormValid = isFormValidResult.status
        )
    }

    private fun register() {
        Log.d(TAG, "Inside_register")
        viewModelScope.launch {
            val createSuccess = userRepository.createUser(
                username = registrationUIState.value.username,
                email = registrationUIState.value.email,
                password = registrationUIState.value.password
            )
            if(createSuccess.isSuccess) {
                _navigationCommand.emit(NavigationCommand.NavigateTo(Routes.HOME_SCREEN))
            } else {
                _registerResult.emit(createSuccess)
            }
        }
    }
}