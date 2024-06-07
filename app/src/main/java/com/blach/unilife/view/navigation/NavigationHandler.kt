package com.blach.unilife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blach.unilife.view.navigation.Routes
import com.blach.unilife.view.screens.HomeScreen
import com.blach.unilife.view.screens.auth.LoginScreen
import com.blach.unilife.view.screens.auth.RegisterScreen
import com.blach.unilife.view.screens.calendar.CalendarScreen
import com.blach.unilife.view.screens.calendar.NewCalendarEventScreen
import com.blach.unilife.view.screens.expenses.AllExpensesTab
import com.blach.unilife.view.screens.expenses.ExpenseTrackerScreen
import com.blach.unilife.view.screens.expenses.NewExpenseScreen
import com.blach.unilife.view.screens.notes.NotesScreen
import com.blach.unilife.view.screens.notes.SingleNoteScreen
import com.blach.unilife.view.screens.todo.TodoDoneScreen
import com.blach.unilife.view.screens.todo.TodoScreen
import com.blach.unilife.view.screens.todo.TodoTabsScreen
import com.blach.unilife.viewmodels.auth.LoginViewModel
import com.blach.unilife.viewmodels.auth.RegistrationViewModel
import com.blach.unilife.viewmodels.calendar.CalendarViewModel
import com.blach.unilife.viewmodels.calendar.NewCalendarEventViewModel
import com.blach.unilife.viewmodels.expenses.AddOrEditExpenseViewModel
import com.blach.unilife.viewmodels.expenses.ExpenseTrackerViewModel
import com.blach.unilife.viewmodels.notes.NotesViewModel
import com.blach.unilife.viewmodels.todo.TodoViewModel

@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()
    val notesViewModel: NotesViewModel = hiltViewModel()
    val todoViewModel: TodoViewModel = hiltViewModel()
    val expenseTrackerViewModel: ExpenseTrackerViewModel = hiltViewModel()
    
    NavHost(navController = navController, startDestination = Routes.LOGIN_SCREEN) {
        composable(Routes.LOGIN_SCREEN) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }
        composable(Routes.REGISTER_SCREEN) {
            val registrationViewModel: RegistrationViewModel = hiltViewModel()
            RegisterScreen(navController = navController, viewModel = registrationViewModel)
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
        composable(Routes.CALENDAR_SCREEN) {
            val calendarViewModel: CalendarViewModel = hiltViewModel()
            CalendarScreen(navController = navController, viewModel = calendarViewModel)
        }
        composable(Routes.NEW_CALENDAR_EVENT_SCREEN + "/{eventId}") { navBackStackEntry ->
            val eventId = navBackStackEntry.arguments?.getString("eventId")
            val newCalendarEventViewModel: NewCalendarEventViewModel = hiltViewModel()
            NewCalendarEventScreen(navController = navController, viewModel = newCalendarEventViewModel, eventId = eventId)
        }
        composable(Routes.NOTES_SCREEN) {
            NotesScreen(navController = navController, viewModel = notesViewModel)
        }
        composable(Routes.SINGLE_NOTE_SCREEN) {
            SingleNoteScreen(navController = navController, viewModel = notesViewModel)
        }
        composable(Routes.TODO_SCREEN) {
            TodoScreen(viewModel = todoViewModel)
        }
        composable(Routes.TODO_DONE_SCREEN) {
            TodoDoneScreen(viewModel = todoViewModel)
        }
        composable(Routes.TODO_TABS_SCREEN) {
            TodoTabsScreen(navController = navController, viewModel = todoViewModel)
        }
        composable(Routes.EXPENSE_TRACKER_SCREEN) {
            ExpenseTrackerScreen(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.NEW_EXPENSE_SCREEN + "/{expenseId}") { navBackStackEntry ->
            val expenseId = navBackStackEntry.arguments?.getString("expenseId")
            val addOrEditExpenseViewModel: AddOrEditExpenseViewModel = hiltViewModel()
            NewExpenseScreen(navController = navController, viewModel = addOrEditExpenseViewModel, expenseId = expenseId)
        }
        composable(Routes.ALL_EXPENSES_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.HOME_AND_BILLS_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.FOOD_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.HEALTH_AND_BEAUTY_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.ENTERTAINMENT_AND_TRAVEL_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.CLOTHES_AND_ACCESSORIES_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.EDUCATION_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.TRANSPORT_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
        composable(Routes.OTHER_TAB) {
            AllExpensesTab(navController = navController, viewModel = expenseTrackerViewModel)
        }
    }

}