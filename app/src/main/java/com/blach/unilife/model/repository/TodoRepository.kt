package com.blach.unilife.model.repository

import android.util.Log
import com.blach.unilife.common.SessionManager
import com.blach.unilife.model.data.todo.TodoTask
import com.blach.unilife.model.data.todo.TodoTaskDTO
import com.blach.unilife.model.mappers.TodoTaskMapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val sessionManager: SessionManager,
    private val mapper: TodoTaskMapper
) {
    private val _tasksFlow = MutableStateFlow<List<TodoTask>>(emptyList())
    val tasksFlow = _tasksFlow.asStateFlow()

    fun getTodoTasksForUser() {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("todos")
                .addSnapshotListener { snapshot, e ->
                    if(e != null || snapshot == null) {
                        _tasksFlow.value = emptyList()
                        return@addSnapshotListener
                    }

                    val tasks = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(TodoTaskDTO::class.java)?.let { dto ->
                            mapper.fromDTO(dto, doc.id)
                        }
                    }

                    _tasksFlow.value = tasks
                }
        }
    }

    fun saveTaskForUser(task: TodoTask) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(task)
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("todos")
                .add(dto)
                .addOnSuccessListener { Log.d("FirestoreSave", "Todo task added successfully") }
                .addOnFailureListener { e -> Log.d("FirestoreSave", "Error adding todo task: ${e.message}")}
        }
    }

    fun updateTaskForUser(task: TodoTask) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(task)
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("todos")
                .document(task.id)
                .set(dto)
                .addOnSuccessListener { Log.d("FirestoreUpdate", "Todo task updated successfully") }
                .addOnFailureListener{ e -> Log.d("FirestoreUpdate", "Error updating todo task: ${e.message}")}
        }
    }

    fun deleteTaskForUser(taskId: String) {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("todos")
                .document(taskId)
                .delete()
                .addOnSuccessListener { Log.d("FirestoreDelete", "Todo task deleted successfully") }
                .addOnFailureListener{ e -> Log.d("FirestoreDelete", "Error deleting todo task: ${e.message}")}
        }
    }

    fun deleteDeprecatedTasks() {
        tasksFlow.value.forEach() { task ->
            if (task.creationDate.isBefore(LocalDate.now().minusWeeks(2))) {
                deleteTaskForUser(task.id)
            }
        }
    }
}