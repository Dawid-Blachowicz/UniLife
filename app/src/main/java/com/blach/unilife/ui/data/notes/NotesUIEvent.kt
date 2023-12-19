package com.blach.unilife.ui.data.notes

import com.blach.unilife.model.data.notes.Note
import java.time.LocalDate

sealed class NotesUIEvent {
    data class TitleChanged(val title: String): NotesUIEvent()
    data class ContentChanged(val content: String): NotesUIEvent()
    data class LastEditDateChanged(val lastEditDate: LocalDate): NotesUIEvent()
    data class LeadNote(val note: Note): NotesUIEvent()
    data class OpenDeleteDialogChanged(val openDeleteDialog: Boolean): NotesUIEvent()

    data object SaveButtonClicked: NotesUIEvent()

    data object ConfirmDeleteButtonClicked: NotesUIEvent()
}
