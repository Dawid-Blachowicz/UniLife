package com.blach.unilife.ui.data

import com.blach.unilife.model.data.Note
import java.time.LocalDate

data class NotesUIState(
    val title: String = "",
    val content: String = "",
    val lastEditDate: LocalDate = LocalDate.now(),
    val notesForLoggedUser: List<Note> = emptyList()
)
