package com.ai.common_domain.entities

interface NoteObject {
    val name: String
    fun isRelated(noteObject: NoteObject): Boolean
}