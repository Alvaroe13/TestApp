package com.ai.common_android_data

import com.ai.common_android_data.datasource.NotesDao
import com.ai.common_android_data.mapper.NoteMapper

import com.ai.common_domain.respository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val noteMapper: NoteMapper
): NotesRepository {


}