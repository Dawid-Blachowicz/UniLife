package com.blach.unilife.ui.data

sealed class NotesUIEvent {
    data class TitleChanged(val title: String): NotesUIEvent()
    data class ContentChanged(val content: String): NotesUIEvent()

    data object SaveButtonClicked: NotesUIEvent()
}
