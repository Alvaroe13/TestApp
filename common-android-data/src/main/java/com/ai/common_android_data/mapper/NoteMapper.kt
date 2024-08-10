package com.ai.common_android_data.mapper

import com.ai.common_android_data.response.NoteDto
import com.ai.common_domain.entities.NoteEntity
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun map(noteDto: NoteDto) : NoteEntity {
        return NoteEntity(

        )
    }
}