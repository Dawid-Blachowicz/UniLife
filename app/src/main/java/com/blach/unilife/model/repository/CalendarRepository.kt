package com.blach.unilife.model.repository

import android.util.Log
import com.blach.unilife.common.SessionManager
import com.blach.unilife.model.data.calendar.CalendarEvent
import com.blach.unilife.model.data.calendar.CalendarEventDTO
import com.blach.unilife.model.mappers.CalendarEventMapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val mapper: CalendarEventMapper,
    private val sessionManager: SessionManager
) {
    private val _eventsFlow = MutableStateFlow<List<CalendarEvent>>(emptyList())
    val eventsFlow = _eventsFlow.asStateFlow()

    fun getEventsForUser() {
        val userId = sessionManager.currentUserId
        if (userId != null) {
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("events")
                .addSnapshotListener { snapshot, e ->
                    if (e != null || snapshot == null) {
                        _eventsFlow.value = emptyList()
                        return@addSnapshotListener
                    }

                    val events = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(CalendarEventDTO::class.java)?.let {mapper.fromDTO(it) }
                    }
                    _eventsFlow.value = events
                }
        }
    }

    fun saveEventForUser(event: CalendarEvent) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(event)
        if (userId != null) {
            firebaseFirestore.collection("users")
                .document(userId)
                .collection("events")
                .add(dto)
                .addOnSuccessListener { Log.d("FirestoreSave", "Event added successfully") }
                .addOnFailureListener { e -> Log.d("FirestoreSave", "Error adding event: ${e.message}") }

        }
    }
}