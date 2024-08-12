package com.ai.common_android_data.repository

import com.ai.common_android_data.datasource.NotesDao
import com.ai.common_android_data.mapper.NoteMapper
import com.ai.common_android_data.utils.safeCall
import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val noteMapper: NoteMapper
): NotesRepository {

    private var notesCache: MutableSet<NoteEntity>? = null
    override suspend fun getAllNotes(): ResultWrapper<List<NoteEntity>> {
        return if (notesCache != null) {
            safeCall {
                notesCache!!.toList()
            }
        } else {
            safeCall {
                notesDao.getAllNotes().map { noteMapper.mapTo(it) }.also {
                    if (notesCache == null) {
                        notesCache = mutableSetOf()
                    }
                    notesCache?.addAll(it)
                }
            }
        }
    }

    override suspend fun insertNote(note: NoteEntity): ResultWrapper<Long> {
        return safeCall {
            notesCache?.add(note)
            notesDao.insertNote( noteMapper.mapFrom(note) )
        }
    }

    override suspend fun updateNote(note: NoteEntity): ResultWrapper<Int> {
        return safeCall {
            notesDao.updateNote(noteMapper.mapFrom(note))
        }
    }

    override suspend fun deleteNote(note: NoteEntity): ResultWrapper<Int> {
        return safeCall {
            notesCache?.removeIf { note.id == it.id }
            notesDao.deleteNote(noteMapper.mapFrom(note))
        }
    }

}