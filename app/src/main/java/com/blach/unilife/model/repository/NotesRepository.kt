package com.blach.unilife.model.repository

import android.util.Log
import com.blach.unilife.common.SessionManager
import com.blach.unilife.model.data.Note
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotesRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val sessionManager: SessionManager
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

                    val events = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Note::class.java) }

                    _notesFlow.value = events
                }
        }
    }

    fun saveNoteForUser(note: Note) {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("notes")
                .add(note)
                .addOnSuccessListener { Log.d("FirestoreSave", "Note added successfully") }
                .addOnFailureListener { e -> Log.d("FirestoreSave", "Error adding note: ${e.message}")}
        }
    }

    fun updateNoteForUser(noteId: String, updatedNote: Note) {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("notes")
                .document(noteId)
                .set(updatedNote)
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