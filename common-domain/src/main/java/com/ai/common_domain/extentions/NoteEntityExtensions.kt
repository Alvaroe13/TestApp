package com.ai.common_domain.extentions

import com.ai.common_domain.entities.NoteEntity

//this example is not the best as is occurring in one place only but for ref is ok
infix fun NoteEntity.getRelatedNonInclusive(
    notes: List<NoteEntity>
) : List<NoteEntity> {
    return notes.filter { this.type.isRelated(it.type) && this.id != it.id  }
}