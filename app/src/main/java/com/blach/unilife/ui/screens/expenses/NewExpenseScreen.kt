package com.blach.unilife.ui.screens.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.blach.unilife.R
import com.blach.unilife.ui.components.ActionButton
import com.blach.unilife.ui.components.AmountTextField
import com.blach.unilife.ui.components.AppTopBar
import com.blach.unilife.ui.components.CategoryDropdown
import com.blach.unilife.ui.components.DescriptionTextField
import com.blach.unilife.ui.components.MyDatePicker
import com.blach.unilife.ui.data.expenses.AddOrEditExpenseUIEvent
import com.blach.unilife.ui.navigation.Routes
import com.blach.unilife.viewmodels.expenses.AddOrEditExpenseViewModel

@Composable
fun NewExpenseScreen(navController: NavController, viewModel: AddOrEditExpenseViewModel, expenseId: String?) {
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
                    viewModel.onEvent(AddOrEditExpenseUIEvent.SaveButtonClicked)
                    navController.navigate(Routes.EXPENSE_TRACKER_SCREEN)
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

                AmountTextField(
                    textValue = uiState.amount.toString(),
                    onTextChange = {
                        viewModel.onEvent(AddOrEditExpenseUIEvent.AmountChanged(it))
                    }
                )

                CategoryDropdown(
                    label = stringResource(R.string.category),
                    selectedCategory = uiState.category,
                    isDropdownExpanded = uiState.isCategoryDropDownExpanded,
                    onCategorySelected = { category ->
                        viewModel.onEvent(AddOrEditExpenseUIEvent.CategoryChanged(category))
                    },
                    onToggleDropdown = {
                        viewModel.onEvent(AddOrEditExpenseUIEvent.ToggleCategoryDropDown)
                    }
                )

                Button(onClick = { viewModel.onEvent(AddOrEditExpenseUIEvent.IsDatePickerDialogOpenChanged(true)) })  {
                    Text(text = stringResource(R.string.date))
                }
                if (uiState.isDatePickerDialogOpen) {
                    MyDatePicker(
                        isDialogOpen = uiState.isDatePickerDialogOpen,
                        onDialogDismiss = {
                            viewModel.onEvent(AddOrEditExpenseUIEvent.IsDatePickerDialogOpenChanged(false))
                        },
                        onDateSelected = { newDate ->
                            viewModel.onEvent(AddOrEditExpenseUIEvent.DateChanged(newDate))
                            viewModel.onEvent(AddOrEditExpenseUIEvent.IsDatePickerDialogOpenChanged(false))
                        },
                        initialDate = uiState.date
                    )
                }

                DescriptionTextField(
                    textValue = uiState.description ?: "",
                    onTextChange = {
                        viewModel.onEvent(AddOrEditExpenseUIEvent.DescriptionChanged(it))
                    })
            }
        }
    }
}