package com.blach.unilife.common

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid
}