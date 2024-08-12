package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
class Desk : NoteObject {

    override val name: String
        get() = Desk::class.simpleName!!

    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Keyboard ||
                noteObject is Human ||
                noteObject is Computer
    }
}
