package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
class Computer : NoteObject {
    override val name: String
        get() = Computer::class.simpleName!!

    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Server ||
                noteObject is Keyboard
    }
}