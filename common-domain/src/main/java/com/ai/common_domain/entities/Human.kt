package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
class Human : NoteObject{

    override val name: String
        get() = Human::class.simpleName!!

    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Desk ||
                 noteObject is Computer
    }

}