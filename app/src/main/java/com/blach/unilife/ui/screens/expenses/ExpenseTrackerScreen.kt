package com.blach.unilife.ui.screens.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.components.ActionButton
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.CurrentMonthRow
import com.blach.unilife.ui.components.ExpenseDonutChart
import com.blach.unilife.ui.components.ExpenseTabRow
import com.blach.unilife.ui.data.expenses.ExpenseTrackerUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.expenses.ExpenseTrackerViewModel

@Composable
fun ExpenseTrackerScreen(navController: NavController, viewModel: ExpenseTrackerViewModel) {
    Scaffold(
        topBar = {
            AppTopBar(
                topBarTitle = stringResource(R.string.expense_tracker),
                homeButtonClicked = {
                    navController.navigate(Routes.HOME_SCREEN)
                })
        },
        floatingActionButton = {
            ActionButton(
                icon = Icons.Default.Add,
                onClick = {
                   navController.navigate("${Routes.NEW_EXPENSE_SCREEN}/add")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column {
                val uiState by viewModel.uiState.collectAsState()

                ExpenseDonutChart(
                    pieChartData = uiState.pieChartData,
                    onCategorySelected = {
                        viewModel.onEvent(ExpenseTrackerUIEvent.ChartSliceClicked(it))
                    })

                CurrentMonthRow(
                    currentMonth = uiState.currentMonth
                ) {
                    viewModel.onEvent(ExpenseTrackerUIEvent.CurrentMonthChanged(it))
                }

                ExpenseTabRow(
                    selectedTabIndex = uiState.selectedTabIndex,
                    onTabSelected = {
                        viewModel.onEvent(ExpenseTrackerUIEvent.SelectedTabIndexChanged(it))
                    })

                when (uiState.selectedTabIndex) {
                    0 -> AllExpensesTab(navController = navController, viewModel = viewModel)
                    1 -> HomeAndBillsExpensesTab(navController = navController, viewModel = viewModel)
                    2 -> FoodExpensesTab(navController = navController, viewModel = viewModel)
                    3 -> HealthAndBeautyExpensesTab(navController = navController, viewModel = viewModel)
                    4 -> EntertainmentAndTravelExpensesTab(navController = navController, viewModel = viewModel)
                    5 -> ClothesAndAccessoriesExpensesTab(navController = navController, viewModel = viewModel)
                    6 -> EducationExpensesTab(navController = navController, viewModel = viewModel)
                    7 -> TransportExpensesTab(navController = navController, viewModel = viewModel)
                    8 -> OtherExpensesTab(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}