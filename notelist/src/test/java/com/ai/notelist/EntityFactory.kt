package com.ai.notelist

import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.entities.Server
import kotlin.random.Random

object EntityFactory {

    private const val ID_TEST = 1

    fun getNoteEntity(): NoteEntity {
        return NoteEntity(
            id = ID_TEST,
            name ="Anything",
            description = "Any",
            type = Human()
        )
    }

    fun getNotEntityList() = buildList {
        add(
            getNoteEntity()
        )
        add(
            NoteEntity(
                id = Random.nextInt(2, 30),
                name ="This is a Desk name",
                description = "This is Desk description",
                type = Desk()
            )
        )
        add(
            NoteEntity(
                id = Random.nextInt(31, 50),
                name ="This is a Desk name",
                description = "This is Desk description",
                type = Server()
            )
        )
    }

}