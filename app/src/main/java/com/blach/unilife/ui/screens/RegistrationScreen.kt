package com.blach.unilife.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.data.RegistrationUIEvent
import com.blach.unilife.ui.components.ButtonComponent
import com.blach.unilife.ui.components.ClickableLoginOrRegisterTextComponent
import com.blach.unilife.ui.components.DividerTextComponent
import com.blach.unilife.ui.components.HeadingTextComponent
import com.blach.unilife.ui.components.MyTextField
import com.blach.unilife.ui.components.NormalTextComponent
import com.blach.unilife.ui.components.PasswordTextField
import com.blach.unilife.ui.navigation.NavigationCommand
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.RegistrationViewModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegistrationViewModel) {
    Surface (
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = stringResource(id = R.string.hello))
            HeadingTextComponent(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(20.dp))

            val uiState by viewModel.registrationUIState.collectAsState()

            MyTextField(
                labelValue = stringResource(id = R.string.username),
                textValue = uiState.username,
                onTextChange = {
                    viewModel.onEvent(RegistrationUIEvent.UsernameChanged(it))
                },
                painterResource(
                id = R.drawable.profile),
                errorStatus = uiState.usernameError)

            MyTextField(
                labelValue = stringResource(id = R.string.email),
                textValue = uiState.email,
                onTextChange = {
                    viewModel.onEvent(RegistrationUIEvent.EmailChanged(it))
                },
                painterResource(
                id = R.drawable.mail),
                errorStatus = uiState.emailError)

            PasswordTextField(
                labelValue = stringResource(id = R.string.password),
                textValue = uiState.password,
                onTextChange = {
                    viewModel.onEvent(RegistrationUIEvent.PasswordChanged(it))
                },
                painterResource(
                id = R.drawable.lock),
                errorStatus = uiState.passwordError)

            Spacer(modifier = Modifier.height(200.dp))
            ButtonComponent(
                value = stringResource(id = R.string.register),
                onButtonClicked = {
                viewModel.onEvent(RegistrationUIEvent.RegisterButtonClicked)
            },
                isEnabled = uiState.isFormValid)

            DividerTextComponent()
            ClickableLoginOrRegisterTextComponent(tryingToLogin = true, onTextSelected = {
                navController.navigate(Routes.LOGIN_SCREEN)
            })
        }

    }

    LaunchedEffect(key1 = true) {
        viewModel.navigationCommand.collect { command ->
            when(command) {
                is NavigationCommand.NavigateTo -> navController.navigate(Routes.HOME_SCREEN)
            }
        }
    }

    val registerResult by viewModel.registerResult.collectAsState(initial = null)
    LaunchedEffect(registerResult) {}
    registerResult?.let { result ->
        if(result.exceptionOrNull() is FirebaseAuthUserCollisionException) {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.email_taken_err), Toast.LENGTH_LONG).show()
        }
    }
}
