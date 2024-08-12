package com.ai.common_android_data.mapper

import com.ai.common_android_data.response.NoteDto
import com.ai.common_domain.entities.NoteEntity
import javax.inject.Inject
import kotlin.random.Random

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
            id = noteEntity.id ?: Random.nextInt(), // we could/should apply a better logic for a real production app but for this sample it gets the job done
            name = noteEntity.name,
            descrption = noteEntity.description,
            type = noteEntity.type
        )
    }
}