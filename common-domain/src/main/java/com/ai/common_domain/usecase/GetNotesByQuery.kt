package com.ai.common_domain.usecase

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import javax.inject.Inject

class GetNotesByQuery @Inject constructor(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(query: String) : ResultWrapper<List<NoteEntity>> {
       val result = repository.getAllNotes()
        if (result is ResultWrapper.Success){
            val notes = result.data.filter {
                it.type.name.contains(query, ignoreCase = true) ||
                        it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            return ResultWrapper.Success(notes)
        }
        return result
    }
}