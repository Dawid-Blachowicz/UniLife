package com.blach.unilife.common

import com.blach.unilife.model.mappers.CalendarEventMapper
import com.blach.unilife.model.mappers.NoteMapper
import com.blach.unilife.model.repository.CalendarRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return  FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideCalendarEventMapper(): CalendarEventMapper {
        return CalendarEventMapper
    }

    @Provides
    @Singleton
    fun provideNoteMapper(): NoteMapper {
        return NoteMapper
    }
}