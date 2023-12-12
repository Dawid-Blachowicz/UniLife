package com.blach.unilife.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnilifeViewModel @Inject constructor(): ViewModel() {

    val value = "value"

    init {
        Log.d(TAG, "Init block of UnilifeViewModel")
    }

    companion object {
        const val TAG = "UnilifeViewModel"
    }

}