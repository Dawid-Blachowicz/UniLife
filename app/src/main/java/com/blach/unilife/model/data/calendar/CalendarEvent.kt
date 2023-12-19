package com.blach.unilife.model.data.calendar

import java.time.LocalDate
import java.time.LocalTime

sealed class CalendarEvent {
    abstract val id: String
    abstract val title: String
    abstract val startTime: LocalTime
    abstract val endTime: LocalTime
    abstract val description: String?

    data class Academic(
        override val id: String,
        override val title: String,
        override val startTime: LocalTime,
        override val endTime: LocalTime,
        override val description: String? = null,

        val day: String,
        val professor: String,
        val roomNumber: String,
        val building: String
        ): CalendarEvent()

    data class Personal(
        override val id: String,
        override val title: String,
        override val startTime: LocalTime,
        override val endTime: LocalTime,
        override val description: String? = null,

        val date: LocalDate
        ): CalendarEvent()
}
