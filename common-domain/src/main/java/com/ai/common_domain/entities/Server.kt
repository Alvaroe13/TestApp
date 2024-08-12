package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
class Server : NoteObject {

    override val name: String
        get() = Server::class.simpleName!!

    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Computer
    }
}