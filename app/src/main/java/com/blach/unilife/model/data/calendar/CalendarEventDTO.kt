package com.blach.unilife.model.data.calendar

data class CalendarEventDTO(
    val title: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val description: String? = null,
    val type: String = "",
    val professor: String? = null,
    val roomNumber: String? = null,
    val building: String? = null,
    val day: String? = null,
    val date: String? = null
)
