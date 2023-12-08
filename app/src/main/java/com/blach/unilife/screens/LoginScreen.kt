package com.blach.unilife.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blach.unilife.R
import com.blach.unilife.components.ButtonComponent
import com.blach.unilife.components.ClickableLoginOrRegisterTextComponent
import com.blach.unilife.components.DividerTextComponent
import com.blach.unilife.components.HeadingTextComponent
import com.blach.unilife.components.MyTextField
import com.blach.unilife.components.NormalTextComponent
import com.blach.unilife.components.PasswordTextField
import com.blach.unilife.navigation.Router
import com.blach.unilife.navigation.Screen
import com.blach.unilife.navigation.SystemBackButtonHandler

@Composable
fun LoginScreen() {
    Surface (
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponent(value = stringResource(id = R.string.hello))
            HeadingTextComponent(value = stringResource(id = R.string.login))
            Spacer(modifier = Modifier.height(20.dp))


            var emailValue by remember {
                mutableStateOf("")
            }
            var passwordValue by remember {
                mutableStateOf("")
            }
            
            MyTextField(
                labelValue = stringResource(id = R.string.email),
                textValue = emailValue ,
                onTextChange = { emailValue = it} ,
                painterResource = painterResource(id = R.drawable.mail))

            PasswordTextField(
                labelValue = stringResource(id = R.string.password),
                textValue = passwordValue,
                onTextChange = { passwordValue = it } ,
                painterResource = painterResource(id = R.drawable.lock))

            Spacer(modifier = Modifier.height(280.dp))
            ButtonComponent(value = stringResource(id = R.string.login), onButtonClicked = { /*TODO*/ })

            DividerTextComponent()
            ClickableLoginOrRegisterTextComponent(tryingToLogin = false, onTextSelected = {
                Router.navigateTo(Screen.RegisterScreen)
            })


        }
    }

    SystemBackButtonHandler {
        Router.navigateTo(Screen.RegisterScreen)
    }
}

@Preview
@Composable
fun DefaultLoginScreenPreview() {
    LoginScreen()
}


