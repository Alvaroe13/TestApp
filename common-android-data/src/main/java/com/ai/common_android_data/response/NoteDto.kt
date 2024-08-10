package com.ai.common_android_data.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.common_android_data.datasource.NotesDatabase

@Entity(tableName = NotesDatabase.DATABASE_NAME)
data class NoteDto(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var title: String = "",
    var descrption: String = ""
)
