package com.blach.unilife.viewmodels.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.model.data.calendar.CalendarEvent
import com.blach.unilife.model.repository.CalendarRepository
import com.blach.unilife.ui.data.calendar.CalendarEventUIEvent
import com.blach.unilife.ui.data.calendar.CalendarEventUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCalendarEventViewModel @Inject constructor(
    private val repository: CalendarRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(CalendarEventUIState())
    val uiState = _uiState.asStateFlow()

    private var currentEventId: String? = null

    init {
        viewModelScope.launch {
            resetCalendarEventState()
            val eventId = savedStateHandle.get<String>("eventId")
            if(!eventId.equals("add")) {
                currentEventId = eventId
                loadEvent(eventId)
            }
        }
    }

    val dayOfWeekMap = mapOf(
        "Poniedziałek" to "MONDAY",
        "Wtorek" to "TUESDAY",
        "Środa" to "WEDNESDAY",
        "Czwartek" to "THURSDAY",
        "Piątek" to "FRIDAY",
        "Sobota" to "SATURDAY",
        "Niedziela" to "SUNDAY"
    )

    fun onEvent(event: CalendarEventUIEvent) {
        when(event) {
            is CalendarEventUIEvent.BuildingChanged -> {
                _uiState.value = _uiState.value.copy(
                    building = event.building
                )
            }
            is CalendarEventUIEvent.DateChanged -> {
                _uiState.value = _uiState.value.copy(
                    date = event.date
                )
            }
            is CalendarEventUIEvent.DayChanged -> {
                _uiState.value = _uiState.value.copy(
                    day = event.day
                )
            }
            is CalendarEventUIEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(
                    description = event.description
                )
            }
            is CalendarEventUIEvent.EndTimeChanged -> {
                _uiState.value = _uiState.value.copy(
                    endTime = event.endTime
                )
            }
            is CalendarEventUIEvent.ProfessorChanged -> {
                _uiState.value = _uiState.value.copy(
                    professor = event.professor
                )
            }
            is CalendarEventUIEvent.RoomNumberChanged -> {
                _uiState.value = _uiState.value.copy(
                    roomNumber = event.roomNumber
                )
            }
            is CalendarEventUIEvent.StartTimeChanged -> {
                _uiState.value = _uiState.value.copy(
                    startTime = event.startTime
                )
            }
            is CalendarEventUIEvent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(
                    title = event.title
                )
            }
            is CalendarEventUIEvent.IsAcademicChanged -> {
                _uiState.value = _uiState.value.copy(
                    isAcademic = event.isAcademic
                )
            }
            is CalendarEventUIEvent.OpenStartTimePickerDialog -> {
                _uiState.value = _uiState.value.copy(
                    isStartTimePickerDialogOpen = true
                )
            }
            is CalendarEventUIEvent.CloseStartTimePickerDialog -> {
                _uiState.value = _uiState.value.copy(
                    isStartTimePickerDialogOpen = false
                )
            }
            is CalendarEventUIEvent.OpenEndTimePickerDialog -> {
                _uiState.value = _uiState.value.copy(
                    isStartTimePickerDialogOpen = true
                )
            }
            is CalendarEventUIEvent.CloseEndTimePickerDialog -> {
                _uiState.value = _uiState.value.copy(
                    isStartTimePickerDialogOpen = false
                )
            }
            is CalendarEventUIEvent.OpenDatePickerDialog -> {
                _uiState.value = _uiState.value.copy(
                    isDatePickerDialogOpen = true
                )
            }
            is CalendarEventUIEvent.CloseDatePickerDialog -> {
                _uiState.value = _uiState.value.copy(
                    isDatePickerDialogOpen = false
                )
            }
            is CalendarEventUIEvent.ToggleWeekDayDropDown -> {
                val currentState = _uiState.value
                _uiState.value = currentState.copy(
                    isWeekDayDropDownExpanded = !currentState.isWeekDayDropDownExpanded
                )
            }
            is CalendarEventUIEvent.SaveButtonClicked -> {
                if(currentEventId == null) {
                    saveEvent()
                } else {
                    updateEvent()
                }
                resetCalendarEventState()
            }
        }
    }

    private suspend fun loadEvent(eventId: String?) {
        val event = eventId?.let { repository.getEventByIdForUser(eventId) }

        if (event != null && !uiState.value.isDataLoaded) {
            _uiState.value = _uiState.value.copy(
                title = event.title,
                startTime = event.startTime,
                endTime = event.endTime,
                description = event.description ?: "",
                isDataLoaded = true
            )
            when(event) {
                is CalendarEvent.Academic -> {
                    _uiState.value = _uiState.value.copy(
                        day = event.day,
                        professor = event.professor,
                        roomNumber = event.roomNumber,
                        building = event.building,
                        isAcademic = true
                    )
                }
                is CalendarEvent.Personal -> {
                    _uiState.value = _uiState.value.copy(
                        date = event.date,
                        isAcademic = false
                    )
                }
            }
        }
    }

    private fun saveEvent() {
        val currentState = uiState.value
        val event = if(currentState.isAcademic) {
            CalendarEvent.Academic(
                id = "",
                title = currentState.title,
                startTime = currentState.startTime,
                endTime = currentState.endTime,
                description = currentState.description,
                professor = currentState.professor,
                roomNumber = currentState.roomNumber,
                building = currentState.building,
                day = currentState.day
            )
        } else {
            CalendarEvent.Personal(
                id = "",
                title = currentState.title,
                startTime = currentState.startTime,
                endTime = currentState.endTime,
                description = currentState.description,
                date = currentState.date
            )
        }

        repository.saveEventForUser(event)
    }

    private fun updateEvent() {
        val currentState = uiState.value
        val event = if(currentState.isAcademic) {
            CalendarEvent.Academic(
                id = currentEventId!!,
                title = currentState.title,
                startTime = currentState.startTime,
                endTime = currentState.endTime,
                description = currentState.description,
                professor = currentState.professor,
                roomNumber = currentState.roomNumber,
                building = currentState.building,
                day = currentState.day
            )
        } else {
            CalendarEvent.Personal(
                id = currentEventId!!,
                title = currentState.title,
                startTime = currentState.startTime,
                endTime = currentState.endTime,
                description = currentState.description,
                date = currentState.date
            )
        }

        repository.updateEventForUser(event)
    }


    private fun resetCalendarEventState() {
        currentEventId = null
        _uiState.value = CalendarEventUIState()
    }
}