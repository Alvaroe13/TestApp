package com.ai.common_domain.entities

data class NoteEntity(
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val type: NoteType = NoteType.IDLE
)

enum class NoteType {
    IDLE,
    DESK,
    COMPUTER,
    KEYBOARD,
    SERVER,
    HUMAN
}