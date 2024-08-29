package com.ai.common_domain.usecase

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.extentions.getRelatedNonInclusive
import com.ai.common_domain.respository.NotesRepository
import com.chrynan.inject.Inject

class GetRelatedNotes @Inject constructor(
    private val notesRepository: NotesRepository
){

    suspend operator fun invoke(note: NoteEntity) : ResultWrapper<List<NoteEntity>> {
        val result = notesRepository.getAllNotes()
        return if (result is ResultWrapper.Success) {
            ResultWrapper.Success(note getRelatedNonInclusive result.data)
        } else {
           result
        }
    }
}