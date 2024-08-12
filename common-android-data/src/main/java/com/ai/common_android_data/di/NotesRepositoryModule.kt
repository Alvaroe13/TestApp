package com.ai.common_android_data.di

import android.content.Context
import androidx.room.Room
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_android_data.DispatcherProviderImpl
import com.ai.common_android_data.repository.NotesRepositoryImpl
import com.ai.common_android_data.datasource.NotesDao
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

    @Binds
    @Singleton
    abstract fun dispatcherProvider(dispatcherProviderImpl: DispatcherProviderImpl): DispatcherProvider


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

        @Provides
        @Singleton
        fun provideNoteDao(noteDatabase: NotesDatabase): NotesDao {
            return noteDatabase.getDao()
        }

    }

}