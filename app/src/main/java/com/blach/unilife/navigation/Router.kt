package com.blach.unilife.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    data object RegisterScreen: Screen()
    data object LoginScreen: Screen()

}

object Router {
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.RegisterScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}