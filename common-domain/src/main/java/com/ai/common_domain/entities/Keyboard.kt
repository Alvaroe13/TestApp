package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
class Keyboard : NoteObject {

    override val name: String
        get() = Keyboard::class.simpleName!!

    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Human ||
                noteObject is Computer
    }
}