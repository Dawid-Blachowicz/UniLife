package com.blach.unilife.viewmodels.expenses

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.roundTwoDecimal
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.data.expenses.ExpenseCategory
import com.blach.unilife.model.data.expenses.getCategoryColor
import com.blach.unilife.model.data.expenses.getCategoryDisplayName
import com.blach.unilife.model.repository.ExpenseRepository
import com.blach.unilife.view.data.expenses.ExpenseTrackerUIEvent
import com.blach.unilife.view.data.expenses.ExpenseTrackerUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ExpenseTrackerViewModel @Inject constructor(
    private val repository: ExpenseRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ExpenseTrackerUIState())
    val uiState = _uiState.asStateFlow()

    private val expenses: StateFlow<List<Expense>> = repository.expensesFlow

    var currentExpenseId: String? = null

    init {
        viewModelScope.launch {
            repository.deleteExpensesOlderThanYear()
            repository.getExpensesForUser()
            expenses.collect {
                getUiStateForLoggerUser(it, YearMonth.now())
            }
        }
    }

    fun onEvent(event: ExpenseTrackerUIEvent) {
        when(event) {
            is ExpenseTrackerUIEvent.LeadExpense -> {
                currentExpenseId = event.expense.id
            }
            is ExpenseTrackerUIEvent.ChartSliceClicked -> {
                mapExpenseCategoryToTabNumber(event.category)
                _uiState.value = _uiState.value.copy(
                    selectedTabIndex = mapExpenseCategoryToTabNumber(event.category)
                )
            }
            is ExpenseTrackerUIEvent.SelectedTabIndexChanged -> {
                _uiState.value = _uiState.value.copy(
                    selectedTabIndex = event.selectedTabIndex
                )
            }
            is ExpenseTrackerUIEvent.CurrentMonthChanged -> {
                if(isWithinOneYearRange(event.currentMonth)) {
                    _uiState.value = _uiState.value.copy(
                        currentMonth = event.currentMonth
                    )
                    getUiStateForLoggerUser(expenses.value, event.currentMonth)
                }
            }
            ExpenseTrackerUIEvent.DeleteButtonClicked -> {
                deleteExpense()
                resetExpensesState()
            }
        }
    }

    private fun getUiStateForLoggerUser(expenses: List<Expense>, currentMonth: YearMonth) {
        val allExpenses = expenses
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val homeAndBillsExpenses = expenses
            .filter { it.category == ExpenseCategory.HOME_AND_BILLS }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val foodExpenses = expenses
            .filter { it.category == ExpenseCategory.FOOD }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val healthAndBeautyExpenses = expenses
            .filter { it.category == ExpenseCategory.HEALTH_AND_BEAUTY }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val entertainmentAndTravelExpenses = expenses
            .filter { it.category == ExpenseCategory.ENTERTAINMENT_AND_TRAVEL }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val clothesAndAccessoriesExpenses = expenses
            .filter { it.category == ExpenseCategory.CLOTHES_AND_ACCESSORIES }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

       val educationExpenses = expenses
            .filter { it.category == ExpenseCategory.EDUCATION }
           .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val transportExpenses = expenses
            .filter { it.category == ExpenseCategory.TRANSPORT }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val otherExpenses = expenses
            .filter { it.category == ExpenseCategory.OTHER }
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sortedByDescending { it.date }

        val allExpensesSum = expenses
            .filter { YearMonth.from(it.date).equals(currentMonth) }
            .sumOf { it.amount}

        val pieChartData = generatePieChartData(allExpenses)

        _uiState.value = _uiState.value.copy(
            allExpenses = allExpenses,
            homeAndBillsExpenses = homeAndBillsExpenses,
            foodExpenses = foodExpenses,
            healthAndBeautyExpenses = healthAndBeautyExpenses,
            entertainmentAndTravelExpenses = entertainmentAndTravelExpenses,
            clothesAndAccessoriesExpenses = clothesAndAccessoriesExpenses,
            educationExpenses = educationExpenses,
            transportExpenses = transportExpenses,
            otherExpenses = otherExpenses,
            allExpensesSum = allExpensesSum,
            pieChartData = pieChartData
        )
    }

    private fun generatePieChartData(expenses: List<Expense>): PieChartData {
        if(expenses.isEmpty()) {
            return PieChartData(
                slices = listOf(PieChartData.Slice(
                    label = "Brak",
                    value = 0f,
                    color = Color.LightGray)),
                plotType =  PlotType.Donut)
        }

        val categoryData = expenses.groupBy { it.category }
            .map { (category, expenses) ->
                PieChartData.Slice(
                    value = expenses.sumOf { it.amount }.roundTwoDecimal().toFloat(),
                    label = getCategoryDisplayName(category),
                    color = getCategoryColor(category)
                )
            }

        return PieChartData(
            slices = categoryData,
            plotType = PlotType.Donut
        )
    }

    private fun deleteExpense() {
        repository.deleteExpenseForUser(currentExpenseId!!)
    }

    fun mapExpenseCategoryToTabNumber(category: ExpenseCategory): Int {
        return when(category) {
            ExpenseCategory.HOME_AND_BILLS -> 1
            ExpenseCategory.FOOD -> 2
            ExpenseCategory.HEALTH_AND_BEAUTY -> 3
            ExpenseCategory.ENTERTAINMENT_AND_TRAVEL -> 4
            ExpenseCategory.CLOTHES_AND_ACCESSORIES -> 5
            ExpenseCategory.EDUCATION -> 6
            ExpenseCategory.TRANSPORT -> 7
            ExpenseCategory.OTHER -> 8
        }
    }

    private fun isWithinOneYearRange(monthToCheck: YearMonth): Boolean {
        val now = YearMonth.now()
        val oneYearAgo = now.minusYears(1)
        val oneYearLater = now.plusYears(1)
        return monthToCheck.isAfter(oneYearAgo) && monthToCheck.isBefore(oneYearLater)
    }

    private fun resetExpensesState() {
        currentExpenseId = null
        _uiState.value = ExpenseTrackerUIState()
    }
}