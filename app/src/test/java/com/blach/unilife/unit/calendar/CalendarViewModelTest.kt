package com.blach.unilife.unit.calendar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.blach.unilife.model.mappers.CalendarEventMapper
import com.blach.unilife.model.repository.CalendarRepository
import com.blach.unilife.view.data.calendar.CalendarUIEvent
import com.blach.unilife.viewmodels.calendar.CalendarViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth

@ExperimentalCoroutinesApi
class CalendarViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CalendarRepository
    private lateinit var mapper: CalendarEventMapper
    private lateinit var viewModel: CalendarViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        mapper = mockk(relaxed = true)
        every { repository.eventsFlow } returns MutableStateFlow(listOf())
        viewModel = CalendarViewModel(repository, mapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when DaySelected event, update selected day`() = runTest {
        val testDate = LocalDate.now()
        viewModel.onEvent(CalendarUIEvent.DaySelected(testDate))

        assertEquals(testDate, viewModel.uiState.value.selectedDay)
    }

    @Test
    fun `when DayDialogDismissed event, clear selected day`() = runTest {
        viewModel.onEvent(CalendarUIEvent.DayDialogDismissed)

        assertNull(viewModel.uiState.value.selectedDay)
    }

    @Test
    fun `when CurrentMonthChanged event, update current month`() = runTest {
        val testMonth = YearMonth.now()
        viewModel.onEvent(CalendarUIEvent.CurrentMonthChanged(testMonth))

        assertEquals(testMonth, viewModel.uiState.value.currentMonth)
    }

    @Test
    fun `when EventDeleted event, call deleteEvent`() = runTest {
        val testEventId = "testId"
        viewModel.onEvent(CalendarUIEvent.EventDeleted(testEventId))

        coVerify { repository.deleteEventForUser(testEventId) }
    }

}
