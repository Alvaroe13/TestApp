package com.ai.common_domain.entities

data class NoteEntity(
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val type: NoteType = NoteType.DESK // for testing atm
)

enum class NoteType {
    IDLE,
    DESK,
    COMPUTER,
    KEYBOARD,
    SERVER,
    HUMAN
}