package com.blach.unilife.unit.expenses

import androidx.lifecycle.SavedStateHandle
import com.blach.unilife.model.repository.ExpenseRepository
import com.blach.unilife.view.data.expenses.AddOrEditExpenseUIEvent
import com.blach.unilife.viewmodels.expenses.AddOrEditExpenseViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class AddOrEditExpenseViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: AddOrEditExpenseViewModel
    private val repository: ExpenseRepository = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<String>("expenseId") } returns null
        viewModel = AddOrEditExpenseViewModel(repository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when AmountChanged, update amount in uiState`() {
        // Arrange
        val testAmount = "100.0"

        // Act
        viewModel.onEvent(AddOrEditExpenseUIEvent.AmountChanged(testAmount))

        // Assert
        assertEquals(testAmount, viewModel.uiState.value.amount)
    }

    @Test
    fun `when init, load expense if id provided`() {
        val expenseId = "testId"
        every { savedStateHandle.get<String>("expenseId") } returns expenseId
        viewModel = AddOrEditExpenseViewModel(repository, savedStateHandle)

        coVerify { repository.getExpenseByIdForUser(expenseId) }
    }

}
