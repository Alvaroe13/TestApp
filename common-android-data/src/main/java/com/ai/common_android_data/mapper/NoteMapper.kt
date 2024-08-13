package com.ai.common_android_data.mapper

import com.ai.common_android_data.response.NoteDto
import com.ai.common_domain.entities.NoteEntity
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun mapTo(noteDto: NoteDto) : NoteEntity {
        return NoteEntity(
            id = noteDto.id,
            name = noteDto.name,
            description = noteDto.descrption,
            type = noteDto.type,
        )
    }

    fun mapFrom(noteEntity: NoteEntity): NoteDto {
        return NoteDto(
            id = noteEntity.id,
            name = noteEntity.name,
            descrption = noteEntity.description,
            type = noteEntity.type
        )
    }
}