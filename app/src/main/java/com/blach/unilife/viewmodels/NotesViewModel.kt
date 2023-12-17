package com.blach.unilife.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.model.data.Note
import com.blach.unilife.model.repository.NotesRepository
import com.blach.unilife.ui.data.NotesUIEvent
import com.blach.unilife.ui.data.NotesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(NotesUIState())
    val uiState = _uiState.asStateFlow()

    private val notes: StateFlow<List<Note>> = repository.notesFlow

    init {
        viewModelScope.launch {
            repository.getNotesForUser()
            notes.collect {
                getNotesForLoggedUser(it)
            }
        }
    }

    fun onEvent(event: NotesUIEvent) {
        when(event) {
            is NotesUIEvent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(
                    title = event.title
                )
            }
            is NotesUIEvent.ContentChanged -> {
                _uiState.value = _uiState.value.copy(
                    content = event.content
                )
            }
            is NotesUIEvent.SaveButtonClicked -> {
                saveNote()
            }
        }
    }

    private fun getNotesForLoggedUser(notes: List<Note>) {
        _uiState.value = _uiState.value.copy(
            notesForLoggedUser = notes
        )
    }

    private fun saveNote() {
        val currentState = uiState.value
        val note = Note(
            id = "",
            title = currentState.title,
            content = currentState.content,
            lastEditDate = currentState.lastEditDate
        )

        repository.saveNoteForUser(note)
    }

}