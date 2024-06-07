package com.blach.unilife.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.view.data.auth.LoginUIEvent
import com.blach.unilife.view.data.auth.LoginUIState
import com.blach.unilife.model.repository.UserRepository
import com.blach.unilife.view.navigation.NavigationCommand
import com.blach.unilife.view.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName

    private val _loginUIState = MutableStateFlow(LoginUIState())
    val loginUIState = _loginUIState.asStateFlow()

    private val _navigationCommand = MutableSharedFlow<NavigationCommand>()
    val navigationCommand = _navigationCommand.asSharedFlow()

    private val _loginResult = MutableSharedFlow<Result<Boolean>>()
    val loginResult = _loginResult.asSharedFlow()

    fun onEvent(event: LoginUIEvent) {
        when(event) {
            is LoginUIEvent.EmailChanged -> {
                _loginUIState.value = _loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                _loginUIState.value = _loginUIState.value.copy(
                    password = event.password
                )
            }

            LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
    }

    private fun login() {
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        viewModelScope.launch {
            val loginSuccess = userRepository.loginUser(email, password)
            if(loginSuccess.isSuccess) {
                _navigationCommand.emit(NavigationCommand.NavigateTo(Routes.HOME_SCREEN))
            } else {
                _loginResult.emit(loginSuccess)

            }
        }
    }
}