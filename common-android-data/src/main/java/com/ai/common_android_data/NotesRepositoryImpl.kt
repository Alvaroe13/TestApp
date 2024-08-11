package com.ai.common_android_data

import com.ai.common_android_data.datasource.NotesDao
import com.ai.common_android_data.mapper.NoteMapper
import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val noteMapper: NoteMapper
): NotesRepository {

    private var notesCache: List<NoteEntity>? = null
    override suspend fun getAllNotes(): ResultWrapper<List<NoteEntity>> {
        return safeCall {
            notesDao.getAllNotes().map { noteMapper.mapTo(it) }.also {
                notesCache = it
            }
        }
    }

    override suspend fun insertNote(note: NoteEntity): ResultWrapper<Long> {
        return safeCall {
            notesDao.insertNote( noteMapper.mapFrom(note) )
        }
    }

    override suspend fun updateNote(note: NoteEntity): ResultWrapper<Int> {
        return safeCall {
            notesDao.updateNote(noteMapper.mapFrom(note))
        }
    }

}