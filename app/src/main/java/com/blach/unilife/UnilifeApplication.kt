package com.blach.unilife

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UnilifeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Coming_inside_onCreate")

        FirebaseApp.initializeApp(this)
    }

    companion object {
        const val TAG = "UniLifeApplication"
    }
}