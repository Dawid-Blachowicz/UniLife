package com.blach.unilife.model.mappers

import com.blach.unilife.model.data.Note
import com.blach.unilife.model.data.dto.NoteDTO
import java.time.LocalDate

object NoteMapper {
    fun fromDTO(dto: NoteDTO, id: String): Note {
        return Note(
            id = id,
            title = dto.title,
            content = dto.content,
            lastEditDate = LocalDate.parse(dto.lastEditDate)
        )
    }

    fun toDTO(model: Note): NoteDTO {
        return NoteDTO(
            title = model.title,
            content = model.content,
            lastEditDate = model.lastEditDate.toString()
        )
    }

}