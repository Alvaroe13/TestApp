package com.ai.common_domain.entities

data class NoteEntity(
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val type: NoteObject = Human(),
    //val typeName: String = type.name
)