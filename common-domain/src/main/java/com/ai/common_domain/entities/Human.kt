package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Human(
    override val name: String = Human::class.simpleName!!
) : NoteObject{
    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Desk ||
                 noteObject is Computer
    }

}