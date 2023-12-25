package com.blach.unilife.ui.screens.expenses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.blach.unilife.ui.components.ExpensesTab
import com.blach.unilife.viewmodels.expenses.ExpenseTrackerViewModel

@Composable
fun AllExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.allExpenses)
}

@Composable
fun HomeAndBillsExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.homeAndBillsExpenses)
}

@Composable
fun FoodExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.foodExpenses)
}

@Composable
fun HealthAndBeautyExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.healthAndBeautyExpenses)
}

@Composable
fun EntertainmentAndTravelExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.entertainmentAndTravelExpenses)
}

@Composable
fun ClothesAndAccessoriesExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.clothesAndAccessoriesExpenses)
}

@Composable
fun EducationExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.educationExpenses)
}

@Composable
fun TransportExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.transportExpenses)
}

@Composable
fun OtherExpensesTab(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    ExpensesTab(
        navController = navController,
        viewModel = viewModel,
        expenses = uiState.otherExpenses)
}