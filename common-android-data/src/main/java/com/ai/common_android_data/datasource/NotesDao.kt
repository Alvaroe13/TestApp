package com.ai.common_android_data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ai.common_android_data.response.NoteDto

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteDto): Long

    @Update
    suspend fun updateNote(note: NoteDto) : Int

    @Delete
    suspend fun deleteNote(note: NoteDto) : Int

    @Query("SELECT * FROM noteDb")
    suspend fun getAllNotes() : List<NoteDto>
}