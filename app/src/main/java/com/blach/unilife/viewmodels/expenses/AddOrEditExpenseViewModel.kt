package com.blach.unilife.viewmodels.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.repository.ExpenseRepository
import com.blach.unilife.view.data.expenses.AddOrEditExpenseUIEvent
import com.blach.unilife.view.data.expenses.AddOrEditExpenseUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddOrEditExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(AddOrEditExpenseUIState())
    val uiState = _uiState.asStateFlow()

    private var currentExpenseId: String? = null

    init {
        viewModelScope.launch {
            resetExpensesState()
            val expenseId = savedStateHandle.get<String>("expenseId")
            if(!expenseId.equals("add")) {
                currentExpenseId = expenseId
                loadExpense(expenseId)
            }
        }
    }

    fun onEvent (event: AddOrEditExpenseUIEvent){
        when (event) {
            is AddOrEditExpenseUIEvent.AmountChanged -> {
                _uiState.value = _uiState.value.copy(
                    amount = event.amount
                )
            }
            is AddOrEditExpenseUIEvent.CategoryChanged -> {
                _uiState.value = _uiState.value.copy(
                    category = event.category
                )
            }
            is AddOrEditExpenseUIEvent.DateChanged -> {
                _uiState.value = _uiState.value.copy(
                    date = event.date
                )
            }
            is AddOrEditExpenseUIEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(
                    description = event.description
                )
            }
            is AddOrEditExpenseUIEvent.IsDatePickerDialogOpenChanged -> {
                _uiState.value = _uiState.value.copy(
                    isDatePickerDialogOpen = event.isDatePickerDialogOpen
                )
            }
            AddOrEditExpenseUIEvent.ToggleCategoryDropDown -> {
                val currentState = _uiState.value
                _uiState.value = _uiState.value.copy(
                    isCategoryDropDownExpanded = !currentState.isCategoryDropDownExpanded
                )
            }
            AddOrEditExpenseUIEvent.SaveButtonClicked -> {
                if(currentExpenseId == null) {
                    saveExpense()
                } else {
                    updateExpense()
                }
                resetExpensesState()
            }
        }
    }

    private suspend fun loadExpense(expenseId: String?) {
        val expense = expenseId?.let { repository.getExpenseByIdForUser(it) }

        if (expense != null && !uiState.value.isDataLoaded) {
            _uiState.value = _uiState.value.copy(
                amount = expense.amount.toString(),
                category = expense.category,
                description = expense.description,
                date = expense.date,
                isDataLoaded = true
            )
        }
    }

    private fun saveExpense() {
        val currentState = uiState.value
        val expense = Expense(
            id = "",
            category = currentState.category,
            amount = currentState.amount.toDouble(),
            description = currentState.description,
            date = currentState.date
        )
        repository.saveExpenseForUser(expense)
    }

    private fun updateExpense() {
        val currentState = uiState.value
        val expense = Expense(
            id = currentExpenseId!!,
            category = currentState.category,
            amount = currentState.amount.toDouble(),
            description = currentState.description,
            date = currentState.date
        )
        repository.updateExpenseForUser(expense)
    }

    private fun resetExpensesState() {
        currentExpenseId = null
        _uiState.value = AddOrEditExpenseUIState()
    }
}