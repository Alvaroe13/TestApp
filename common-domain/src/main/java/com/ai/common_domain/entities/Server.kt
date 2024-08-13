package com.ai.common_domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Server(
    override val name: String = Server::class.simpleName!!
) : NoteObject {
    override fun isRelated(noteObject: NoteObject): Boolean {
        return noteObject is Computer ||
                noteObject is Server
    }
}