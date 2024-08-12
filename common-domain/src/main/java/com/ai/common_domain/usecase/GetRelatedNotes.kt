package com.ai.common_domain.usecase

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import com.chrynan.inject.Inject

class GetRelatedNotes @Inject constructor(
    private val notesRepository: NotesRepository
){

    suspend operator fun invoke(note: NoteEntity) : ResultWrapper<List<NoteEntity>> {
        val result = notesRepository.getAllNotes()
        return if (result is ResultWrapper.Success) {
            val relatedNotes = result.data.filter { it.type.isRelated(note.type) }
            ResultWrapper.Success(relatedNotes)
        } else {
           result
        }
    }
}