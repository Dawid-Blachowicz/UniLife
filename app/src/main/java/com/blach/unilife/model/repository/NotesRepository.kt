package com.blach.unilife.model.repository

import android.util.Log
import com.blach.unilife.common.SessionManager
import com.blach.unilife.model.data.Note
import com.blach.unilife.model.data.dto.NoteDTO
import com.blach.unilife.model.mappers.NoteMapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val sessionManager: SessionManager,
    private val mapper: NoteMapper
) {
    private val _notesFlow = MutableStateFlow<List<Note>>(emptyList())
    val notesFlow = _notesFlow.asStateFlow()

    fun getNotesForUser() {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("notes")
                .addSnapshotListener { snapshot, e ->
                    if(e != null || snapshot == null) {
                        _notesFlow.value = emptyList()
                        return@addSnapshotListener
                    }

                    val notes = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(NoteDTO::class.java)?.let { dto ->
                            mapper.fromDTO(dto, doc.id)
                        }
                    }

                    _notesFlow.value = notes
                }
        }
    }

    fun saveNoteForUser(note: Note) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(note)
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("notes")
                .add(dto)
                .addOnSuccessListener { Log.d("FirestoreSave", "Note added successfully") }
                .addOnFailureListener { e -> Log.d("FirestoreSave", "Error adding note: ${e.message}")}
        }
    }

    fun updateNoteForUser(updatedNote: Note) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(updatedNote)
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("notes")
                .document(updatedNote.id)
                .set(dto)
                .addOnSuccessListener { Log.d("FirestoreUpdate", "Note updated successfully") }
                .addOnFailureListener{ e -> Log.d("FirestoreUpdate", "Error updating note: ${e.message}")}
        }
    }

    fun deleteNoteForUser(noteId: String) {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("notes")
                .document(noteId)
                .delete()
                .addOnSuccessListener { Log.d("FirestoreDelete", "Note deleted successfully") }
                .addOnFailureListener{ e -> Log.d("FirestoreDelete", "Error deleting note: ${e.message}")}
        }
    }

}