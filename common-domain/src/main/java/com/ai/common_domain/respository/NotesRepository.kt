package com.ai.common_domain.respository

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity

interface NotesRepository {
    suspend fun getAllNotes(): ResultWrapper<List<NoteEntity>>
    suspend fun insertNote(note: NoteEntity): ResultWrapper<Long>
}