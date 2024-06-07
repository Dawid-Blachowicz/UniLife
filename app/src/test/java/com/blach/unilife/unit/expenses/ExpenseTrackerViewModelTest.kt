package com.blach.unilife.unit.expenses

import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.data.expenses.ExpenseCategory
import com.blach.unilife.model.repository.ExpenseRepository
import com.blach.unilife.view.data.expenses.ExpenseTrackerUIEvent
import com.blach.unilife.viewmodels.expenses.ExpenseTrackerViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class ExpenseTrackerViewModelTest {

    private lateinit var viewModel: ExpenseTrackerViewModel
    private val repository: ExpenseRepository = mockk(relaxed = true)
    private val expensesFlow = MutableStateFlow<List<Expense>>(emptyList())
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { repository.expensesFlow } returns expensesFlow
        viewModel = ExpenseTrackerViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when ChartSliceClicked, update selectedTabIndex`() {
        // Arrange
        val category = ExpenseCategory.FOOD

        // Act
        viewModel.onEvent(ExpenseTrackerUIEvent.ChartSliceClicked(category))

        // Assert
        val expectedTabIndex = viewModel.mapExpenseCategoryToTabNumber(category)
        assertEquals(expectedTabIndex, viewModel.uiState.value.selectedTabIndex)
    }

    @Test
    fun `when init, load expenses`() {
        // Założenie, że inicjalizacja ViewModel ładuje wydatki.
        coVerify { repository.getExpensesForUser() }
    }

    @Test
    fun `when LeadExpense event, set currentExpenseId`() {
        val expenseId = "testId"
        viewModel.onEvent(ExpenseTrackerUIEvent.LeadExpense(Expense(id = expenseId, amount = 1.0, category = ExpenseCategory.FOOD, date = LocalDate.now(), description = "")))
        assertEquals(expenseId, viewModel.currentExpenseId)
    }

}
