package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Computer(
    override val name: String = Computer::class.simpleName!!
) : NoteObject {
    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Server ||
                noteObject is Keyboard
    }
}