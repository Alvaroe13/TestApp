package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Keyboard(
    override val name: String = Keyboard::class.simpleName!!
) : NoteObject {
    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Human ||
                noteObject is Computer ||
                noteObject is Keyboard
    }
}