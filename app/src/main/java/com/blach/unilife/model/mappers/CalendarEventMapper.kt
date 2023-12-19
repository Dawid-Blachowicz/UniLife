package com.blach.unilife.model.mappers

import com.blach.unilife.model.data.calendar.CalendarEvent
import com.blach.unilife.model.data.calendar.CalendarEventType
import com.blach.unilife.model.data.calendar.SimpleCalendarEvent
import com.blach.unilife.model.data.calendar.CalendarEventDTO
import java.time.LocalDate
import java.time.LocalTime

object CalendarEventMapper {
    fun fromDTO(dto: CalendarEventDTO): CalendarEvent {
        return when(dto.type) {
            CalendarEventType.ACADEMIC.toString() -> {
                CalendarEvent.Academic(
                    id = dto.id,
                    title = dto.title,
                    startTime = LocalTime.parse(dto.startTime),
                    endTime = LocalTime.parse(dto.endTime),
                    description = dto.description,
                    professor = dto.professor?: "",
                    roomNumber = dto.roomNumber?: "",
                    building = dto.building?: "",
                    day = dto.day?: ""
                )
            }
            CalendarEventType.PERSONAL.toString() -> {
                CalendarEvent.Personal(
                    id = dto.id,
                    title = dto.title,
                    startTime = LocalTime.parse(dto.startTime),
                    endTime = LocalTime.parse(dto.endTime),
                    description = dto.description,
                    date = LocalDate.parse(dto.date)
                )
            }

            else -> throw IllegalArgumentException("Unknown event type")
        }
    }

    fun toDTO(model: CalendarEvent): CalendarEventDTO {
        return CalendarEventDTO(
            id = model.id,
            title = model.title,
            startTime = model.startTime.toString(),
            endTime = model.endTime.toString(),
            description = model.description,
            type = when (model) {
                is CalendarEvent.Academic -> CalendarEventType.ACADEMIC.toString()
                is CalendarEvent.Personal -> CalendarEventType.PERSONAL.toString()
            },
            professor = if (model is CalendarEvent.Academic) model.professor else null,
            roomNumber = if (model is CalendarEvent.Academic) model.roomNumber else null,
            building = if (model is CalendarEvent.Academic) model.building else null,
            day = if (model is CalendarEvent.Academic) model.day else null,
            date = if (model is CalendarEvent.Personal) model.date.toString() else null
        )
    }

    fun toSimple(model: CalendarEvent): SimpleCalendarEvent {
        return SimpleCalendarEvent(
            type = when (model) {
                is CalendarEvent.Academic -> CalendarEventType.ACADEMIC
                is CalendarEvent.Personal -> CalendarEventType.PERSONAL
            },
            day = if (model is CalendarEvent.Academic) model.day else null,
            date = if (model is CalendarEvent.Personal) model.date else null
        )
    }
}