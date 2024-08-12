package com.ai.common_android_data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.common_android_data.datasource.NotesDatabase
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.NoteObject

@Entity(tableName = NotesDatabase.DATABASE_NAME)
data class NoteDto(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String = "",
    var descrption: String = "",
    var type: NoteObject = Human()
)
