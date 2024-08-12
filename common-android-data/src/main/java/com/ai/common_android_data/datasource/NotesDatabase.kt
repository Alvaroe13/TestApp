package com.ai.common_android_data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ai.common_android_data.response.NoteDto

@Database(entities = [NoteDto::class] , version = NotesDatabase.DATABASE_VERSION)
@TypeConverters(NoteObjectConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    companion object{
        const val DATABASE_NAME = "noteDb"
        const val DATABASE_VERSION = 1
    }

    abstract fun getDao(): NotesDao

}