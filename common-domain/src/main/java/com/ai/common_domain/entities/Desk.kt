package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Desk(
    override val name: String = Desk::class.simpleName!!
) : NoteObject {

    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Keyboard ||
                noteObject is Human ||
                noteObject is Computer
    }
}
