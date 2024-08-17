package com.ai.notelist.repository

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.ResultWrapperError
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository

class NotesRepositoryTestImpl : NotesRepository {

    private var cache : MutableList<NoteEntity>? = null

    fun addCache(list: List<NoteEntity>) {
        if (cache == null) {
            println("NotesRepositoryTestImpl if null block")
            cache = mutableListOf()
        }
        cache?.addAll(list)
    }

    override suspend fun getAllNotes(): ResultWrapper<List<NoteEntity>> {
        return if (cache != null) {
            println("NotesRepositoryTestImpl if block")
            ResultWrapper.Success(cache!!.toList())
        } else {
            println("NotesRepositoryTestImpl else block")
            ResultWrapper.Failure(ResultWrapperError.Unknown)
        }
    }

    override suspend fun insertNote(note: NoteEntity): ResultWrapper<Long> {
        return ResultWrapper.Success(1L)
    }

    override suspend fun updateNote(note: NoteEntity): ResultWrapper<Int> {
        return ResultWrapper.Success(1)
    }

    override suspend fun deleteNote(note: NoteEntity): ResultWrapper<Int> {
        return ResultWrapper.Success(1)
    }
}