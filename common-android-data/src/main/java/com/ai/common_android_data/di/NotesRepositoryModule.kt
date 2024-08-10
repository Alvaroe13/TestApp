package com.ai.common_android_data.di

import android.content.Context
import androidx.room.Room
import com.ai.common_android_data.NotesRepositoryImpl
import com.ai.common_android_data.datasource.NotesDatabase
import com.ai.common_domain.respository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNotesRepo(impl: NotesRepositoryImpl): NotesRepository


    companion object {

        @Provides
        @Singleton
        fun provideNoteDatabase(@ApplicationContext context: Context): NotesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDatabase::class.java,
                NotesDatabase.DATABASE_NAME
            ).build()
        }

    }

}