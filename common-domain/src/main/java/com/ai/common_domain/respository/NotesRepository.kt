package com.ai.common_domain.respository

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity

interface NotesRepository {
    suspend fun getAllNotes(): ResultWrapper<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity): ResultWrapper<Long>
    suspend fun updateNote(note: NoteEntity): ResultWrapper<Int>
    suspend fun deleteNote(note: NoteEntity): ResultWrapper<Int>
}