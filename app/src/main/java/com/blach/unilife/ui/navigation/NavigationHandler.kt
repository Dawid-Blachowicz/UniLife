package com.blach.unilife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blach.unilife.ui.screens.calendar.CalendarScreen
import com.blach.unilife.ui.screens.HomeScreen
import com.blach.unilife.ui.screens.auth.LoginScreen
import com.blach.unilife.ui.screens.calendar.NewCalendarEventScreen
import com.blach.unilife.ui.screens.notes.NotesScreen
import com.blach.unilife.ui.screens.auth.RegisterScreen
import com.blach.unilife.ui.screens.notes.SingleNoteScreen
import com.blach.unilife.viewmodels.calendar.CalendarViewModel
import com.blach.unilife.viewmodels.auth.LoginViewModel
import com.blach.unilife.viewmodels.calendar.NewCalendarEventViewModel
import com.blach.unilife.viewmodels.notes.NotesViewModel
import com.blach.unilife.viewmodels.auth.RegistrationViewModel

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    val notesViewModel: NotesViewModel = hiltViewModel()
    
    NavHost(navController = navController, startDestination = Routes.LOGIN_SCREEN) {
        composable(Routes.LOGIN_SCREEN) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, loginViewModel)
        }
        composable(Routes.REGISTER_SCREEN) {
            val registrationViewModel: RegistrationViewModel = hiltViewModel()
            RegisterScreen(navController, registrationViewModel)
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(Routes.CALENDAR_SCREEN) {
            val calendarViewModel: CalendarViewModel = hiltViewModel()
            CalendarScreen(navController, calendarViewModel)
        }
        composable(Routes.NEW_CALENDAR_EVENT_SCREEN) {
            val newCalendarEventViewModel: NewCalendarEventViewModel = hiltViewModel()
            NewCalendarEventScreen(navController, newCalendarEventViewModel)
        }
        composable(Routes.NOTES_SCREEN) {
            NotesScreen(navController, notesViewModel)
        }
        composable(Routes.SINGLE_NOTE_SCREEN) {
            SingleNoteScreen(navController, notesViewModel)
        }
    }

}