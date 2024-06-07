package com.blach.unilife.view.data.calendar

import java.time.LocalDate
import java.time.LocalTime

sealed class CalendarEventUIEvent {
    data class TitleChanged(val title: String): CalendarEventUIEvent()
    data class StartTimeChanged(val startTime: LocalTime): CalendarEventUIEvent()
    data class EndTimeChanged(val endTime: LocalTime): CalendarEventUIEvent()
    data class DescriptionChanged(val description: String): CalendarEventUIEvent()
    data class DayChanged(val day: String): CalendarEventUIEvent()
    data class ProfessorChanged(val professor: String): CalendarEventUIEvent()
    data class RoomNumberChanged(val roomNumber: String): CalendarEventUIEvent()
    data class BuildingChanged(val building: String): CalendarEventUIEvent()
    data class DateChanged(val date: LocalDate): CalendarEventUIEvent()
    data class IsAcademicChanged(val isAcademic: Boolean): CalendarEventUIEvent()

    data object SaveButtonClicked: CalendarEventUIEvent()
    data object OpenStartTimePickerDialog : CalendarEventUIEvent()
    data object CloseStartTimePickerDialog : CalendarEventUIEvent()
    data object OpenEndTimePickerDialog : CalendarEventUIEvent()
    data object CloseEndTimePickerDialog : CalendarEventUIEvent()
    data object OpenDatePickerDialog: CalendarEventUIEvent()
    data object CloseDatePickerDialog: CalendarEventUIEvent()
    data object ToggleWeekDayDropDown: CalendarEventUIEvent()
}
