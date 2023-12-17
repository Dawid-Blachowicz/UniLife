package com.blach.unilife.model.repository

import android.util.Log
import com.blach.unilife.model.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    private val TAG = UserRepository::class.simpleName

    suspend fun createUser(username: String, email: String, password: String): Result<Boolean> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            authResult.user?.let { firebaseUser ->
                val user = User(
                    id = firebaseUser.uid,
                    username = username,
                    email = email
                )

                firebaseFirestore.collection("users").document(user.id).set(user).await()
                Result.success(true)
            } ?: Result.failure(Exception("Failed to create user"))
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<Boolean> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

}

