package com.blach.unilife.view.data.notes

import com.blach.unilife.model.data.notes.Note
import java.time.LocalDate

data class NotesUIState(
    val title: String = "",
    val content: String = "",
    val lastEditDate: LocalDate = LocalDate.now(),
    val notesForLoggedUser: List<Note> = emptyList(),
    val openDeleteDialog: Boolean = false
)
