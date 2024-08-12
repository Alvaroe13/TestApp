package com.ai.common_android_data.datasource

import androidx.room.TypeConverter
import com.ai.common_domain.entities.Computer
import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.Keyboard
import com.ai.common_domain.entities.NoteObject
import com.ai.common_domain.entities.Server

class NoteObjectConverter {

    @TypeConverter
    fun fromNote(noteType: NoteObject): String {
        return noteType.name
    }

    @TypeConverter
    fun toNote(name: String): NoteObject {
        return when(name) {
            Human::class.simpleName -> Human()
            Computer::class.simpleName -> Computer()
            Server::class.simpleName-> Server()
            Keyboard::class.simpleName -> Keyboard()
            Desk::class.simpleName-> Desk()
            else -> throw IllegalArgumentException("this name doesn't match any of the above")
        }
    }
}