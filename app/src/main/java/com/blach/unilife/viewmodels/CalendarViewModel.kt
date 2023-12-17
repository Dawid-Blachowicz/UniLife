package com.blach.unilife.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.model.data.CalendarEvent
import com.blach.unilife.model.mappers.CalendarEventMapper
import com.blach.unilife.model.repository.CalendarRepository
import com.blach.unilife.ui.data.CalendarUIEvent
import com.blach.unilife.ui.data.CalendarUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val repository: CalendarRepository,
    private val mapper: CalendarEventMapper
): ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUIState())
    val uiState = _uiState.asStateFlow()

    private val events: StateFlow<List<CalendarEvent>> = repository.eventsFlow

    init {
        viewModelScope.launch {
            repository.getEventsForUser()
            events.collect {
                getSimpleEventsForSelectedMonth(YearMonth.now(), it)
            }
        }
    }

    fun onEvent(event: CalendarUIEvent) {
        when(event) {
            is CalendarUIEvent.DaySelected -> {
                _uiState.value = _uiState.value.copy(
                    selectedDay = event.day
                )
                getEventsForSelectedDay(event.day)
            }

            is CalendarUIEvent.DayDialogDismissed -> {
                _uiState.value = _uiState.value.copy(
                    selectedDay = null
                )
            }

            is CalendarUIEvent.CurrentMonthChanged -> {
                _uiState.value = _uiState.value.copy(
                    currentMonth = event.month
                )
                getSimpleEventsForSelectedMonth(event.month, events.value)
            }
        }
    }

    private fun getSimpleEventsForSelectedMonth(selectedMonth: YearMonth, listOfEvents: List<CalendarEvent>) {
        val simpleCalendarEvents = listOfEvents
            .filter { event ->
                when(event) {
                    is CalendarEvent.Personal -> event.date.year == selectedMonth.year && event.date.month == selectedMonth.month
                    is CalendarEvent.Academic -> true
                }
            }
            .map { event ->
                mapper.toSimple(event)
            }

        _uiState.value = _uiState.value.copy(
            simpleEventsForSelectedMonth = simpleCalendarEvents
        )
    }


    private fun getEventsForSelectedDay(selectedDay: LocalDate) {
        val filteredEvents = events.value
            .filter {event ->
                when(event) {
                    is CalendarEvent.Academic -> event.day == selectedDay.dayOfWeek.name
                    is CalendarEvent.Personal -> event.date == selectedDay
                }
            }
            .sortedBy { it.startTime }

        _uiState.value = _uiState.value.copy(
            eventsForSelectedDay = filteredEvents
        )
    }
}