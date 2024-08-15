package com.ai.notelist

import com.ai.common_domain.entities.Computer
import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.Keyboard
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.entities.NoteObject
import com.ai.common_domain.entities.Server
import kotlin.random.Random

object EntityFactory {

    private const val ID_TEST = 1

    fun makeNoteEntity(type: NoteObject = Human() ): NoteEntity {
        return NoteEntity(
            id = ID_TEST,
            name ="Anything",
            description = "Any",
            type = type
        )
    }

    fun getNotEntityList() = buildList {
        add(
            makeNoteEntity()
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
        add(
            NoteEntity(
                id = Random.nextInt(31, 50),
                name ="This is a Desk name",
                description = "This is Desk description",
                type = Keyboard()
            )
        )
        add(
            NoteEntity(
                id = Random.nextInt(31, 50),
                name ="This is a Desk name",
                description = "This is Desk description",
                type = Computer()
            )
        )

    }

}