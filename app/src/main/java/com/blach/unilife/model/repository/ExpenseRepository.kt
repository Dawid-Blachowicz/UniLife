package com.blach.unilife.model.repository

import android.util.Log
import com.blach.unilife.common.SessionManager
import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.data.expenses.ExpenseDTO
import com.blach.unilife.model.mappers.ExpenseMapper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val mapper: ExpenseMapper,
    private val sessionManager: SessionManager
) {
    private val _expensesFlow = MutableStateFlow<List<Expense>>(emptyList())
    val expensesFlow = _expensesFlow.asStateFlow()

    fun getExpensesForUser() {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("expenses")
                .addSnapshotListener { snapshot, e ->
                    if(e != null || snapshot ==  null) {
                        _expensesFlow.value = emptyList()
                        return@addSnapshotListener
                    }

                    val expenses = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ExpenseDTO::class.java)?.let { dto ->
                            mapper.fromDTO(dto, doc.id)
                        }
                    }

                    _expensesFlow.value = expenses
                }
        }
    }

    suspend fun getExpenseByIdForUser(id: String): Expense? {
        val userId = sessionManager.currentUserId ?: return null

        return try {
            val snapshot = firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("expenses")
                .document(id)
                .get()
                .await()

            if (snapshot.exists()) {
                snapshot.toObject(ExpenseDTO::class.java)?.let { dto ->
                    mapper.fromDTO(dto, snapshot.id)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("FirestoreError", "Error getting expense: ${e.message}")
            null
        }
    }

    fun saveExpenseForUser(expense: Expense) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(expense)
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("expenses")
                .add(dto)
                .addOnSuccessListener { Log.d("FirestoreSave", "Expense added successfully") }
                .addOnFailureListener { e -> Log.d("FirestoreSave", "Error adding expense: ${e.message}")}
        }
    }

    fun updateExpenseForUser(expense: Expense) {
        val userId = sessionManager.currentUserId
        val dto = mapper.toDTO(expense)
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("expenses")
                .document(expense.id)
                .set(dto)
                .addOnSuccessListener { Log.d("FirestoreUpdate", "Expense updated successfully") }
                .addOnFailureListener{ e -> Log.d("FirestoreUpdate", "Error updating expense: ${e.message}")}
        }
    }

    fun deleteExpenseForUser(expenseId: String) {
        val userId = sessionManager.currentUserId
        if(userId != null) {
            firebaseFirestore
                .collection("users")
                .document(userId)
                .collection("expenses")
                .document(expenseId)
                .delete()
                .addOnSuccessListener { Log.d("FirestoreDelete", "Expense deleted successfully") }
                .addOnFailureListener{ e -> Log.d("FirestoreDelete", "Error deleting expense: ${e.message}")}
        }
    }

    fun deleteExpensesOlderThanYear() {
        expensesFlow.value.forEach() { expense ->
            if (expense.date.isBefore(LocalDate.now().minusYears(1))) {
                deleteExpenseForUser(expense.id)
            }
        }
    }
}