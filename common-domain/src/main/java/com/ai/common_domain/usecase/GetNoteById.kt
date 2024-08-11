package com.ai.common_domain.usecase

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import com.chrynan.inject.Inject

class GetNoteById @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(noteId: String) : ResultWrapper<NoteEntity> {
        val notes = repository.getAllNotes()
        if (notes is ResultWrapper.Success) {
            val note = notes.data.find { it.id == noteId.toInt() } ?: throw IllegalArgumentException(NOTE_EXCEPTION)
            return ResultWrapper.Success(note)
        }
        throw IllegalArgumentException(NOTE_EXCEPTION)
    }

    companion object {
        internal const val NOTE_EXCEPTION = "NoteId does not exists in db"
    }

}