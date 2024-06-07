package com.blach.unilife.unit.calendar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.blach.unilife.model.repository.CalendarRepository
import com.blach.unilife.view.data.calendar.CalendarEventUIEvent
import com.blach.unilife.viewmodels.calendar.NewCalendarEventViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

@ExperimentalCoroutinesApi
class NewCalendarEventViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CalendarRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: NewCalendarEventViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle()
        viewModel = NewCalendarEventViewModel(repository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `when TitleChanged event, update title`() = runTest {
        val testTitle = "Test Title"
        viewModel.onEvent(CalendarEventUIEvent.TitleChanged(testTitle))
        assertEquals(testTitle, viewModel.uiState.value.title)
    }

    @Test
    fun `when BuildingChanged event, update building`() = runTest {
        val testBuilding = "Building A"
        viewModel.onEvent(CalendarEventUIEvent.BuildingChanged(testBuilding))
        assertEquals(testBuilding, viewModel.uiState.value.building)
    }

    @Test
    fun `when DateChanged event, update date`() = runTest {
        val testDate = LocalDate.of(2023, 4, 15)
        viewModel.onEvent(CalendarEventUIEvent.DateChanged(testDate))
        assertEquals(testDate, viewModel.uiState.value.date)
    }

    @Test
    fun `when DayChanged event, update day`() = runTest {
        val testDay = "Monday"
        viewModel.onEvent(CalendarEventUIEvent.DayChanged(testDay))
        assertEquals(testDay, viewModel.uiState.value.day)
    }

    @Test
    fun `when DescriptionChanged event, update description`() = runTest {
        val testDescription = "New Description"
        viewModel.onEvent(CalendarEventUIEvent.DescriptionChanged(testDescription))
        assertEquals(testDescription, viewModel.uiState.value.description)
    }

    @Test
    fun `when EndTimeChanged event, update endTime`() = runTest {
        val testEndTime = LocalTime.of(17, 30)
        viewModel.onEvent(CalendarEventUIEvent.EndTimeChanged(testEndTime))
        assertEquals(testEndTime, viewModel.uiState.value.endTime)
    }

    @Test
    fun `when ProfessorChanged event, update professor`() = runTest {
        val testProfessor = "Prof. Smith"
        viewModel.onEvent(CalendarEventUIEvent.ProfessorChanged(testProfessor))
        assertEquals(testProfessor, viewModel.uiState.value.professor)
    }

    @Test
    fun `when RoomNumberChanged event, update roomNumber`() = runTest {
        val testRoomNumber = "101"
        viewModel.onEvent(CalendarEventUIEvent.RoomNumberChanged(testRoomNumber))
        assertEquals(testRoomNumber, viewModel.uiState.value.roomNumber)
    }

    @Test
    fun `when StartTimeChanged event, update startTime`() = runTest {
        val testStartTime = LocalTime.of(9, 30)
        viewModel.onEvent(CalendarEventUIEvent.StartTimeChanged(testStartTime))
        assertEquals(testStartTime, viewModel.uiState.value.startTime)
    }

    @Test
    fun `when IsAcademicChanged event, update isAcademic`() = runTest {
        val testIsAcademic = true
        viewModel.onEvent(CalendarEventUIEvent.IsAcademicChanged(testIsAcademic))
        assertEquals(testIsAcademic, viewModel.uiState.value.isAcademic)
    }

}
