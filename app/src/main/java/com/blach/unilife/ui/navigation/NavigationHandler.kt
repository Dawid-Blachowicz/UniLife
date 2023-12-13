package com.blach.unilife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blach.unilife.ui.screens.HomeScreen
import com.blach.unilife.ui.screens.LoginScreen
import com.blach.unilife.ui.screens.RegisterScreen
import com.blach.unilife.viewmodels.LoginViewModel
import com.blach.unilife.viewmodels.RegistrationViewModel

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.LOGIN_SCREEN) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, loginViewModel)
        }
        composable(Routes.REGISTER_SCREEN) {
            val registrationViewModel: RegistrationViewModel = hiltViewModel()
            RegisterScreen(navController, registrationViewModel)
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen()
        }
    }

}