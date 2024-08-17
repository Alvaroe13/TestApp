package com.ai.common_domain

import com.ai.common_domain.entities.Computer
import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.Keyboard
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.entities.NoteObject
import com.ai.common_domain.entities.Server
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetRelatedNotes
import io.mockk.coEvery
import org.junit.Test

import io.mockk.mockk
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.random.Random

class GetRelatedNotesTest {

    private lateinit var getRelatedNotes : GetRelatedNotes
    private lateinit var repository : NotesRepository

    @Before
    fun setUp() {
        repository = mockk<NotesRepository>()
        getRelatedNotes = (GetRelatedNotes(repository))
    }

    @Test
    fun `given note of type server return only related`(): Unit = runTest {

        //given
        val note = makeNoteEntity(type = Server())
        coEvery { repository.getAllNotes() } returns
                ResultWrapper.Success(getNotEntityList())

        //when
        val result = getRelatedNotes.invoke(note)

        //then
        assert( result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success).data
        assert( data.isNotEmpty() )
        assert(
            data.all {
                it.type is Computer ||
                it.type is Server
            }
        )
    }

    @Test
    fun `given note of type server return only related force error`(): Unit = runTest {

        //given
        val note = makeNoteEntity(type = Server())
        coEvery { repository.getAllNotes() } returns
                ResultWrapper.Success(getNotEntityList())

        //when
        val result = getRelatedNotes.invoke(note)

        //then
        assert( result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success).data
        assert( data.isNotEmpty() )
        assert(
            data.any { it.type !is Human &&  it.type !is Keyboard && it.type !is Desk}
        )
    }

    @Test
    fun `given note of type server return only related excluding itself`(): Unit = runTest {
        //given
        val note = makeNoteEntity(type = Server())
        val payloadReturn = getNotEntityList().toMutableList().apply { add(note) }
        coEvery { repository.getAllNotes() } returns
                ResultWrapper.Success(payloadReturn)

        //when
        val result = getRelatedNotes.invoke(note)

        //then
        assert( result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success).data
        assert( data.isNotEmpty() )
        assertNull(data.find { it.id == note.id })
    }

    private fun makeNoteEntity(type: NoteObject = Human() ): NoteEntity {
        return NoteEntity(
            id = 1,
            name ="Anything",
            description = "Any",
            type = type
        )
    }

    private fun getNotEntityList() = buildList {
        add(
            makeNoteEntity()
        )
        add(
            NoteEntity(
                id = Random.nextInt(2, 30),
                name ="Clean the desk",
                description = "Tidy up the room",
                type = Desk()
            )
        )
        add(
            NoteEntity(
                id = Random.nextInt(31, 50),
                name ="Update the software",
                description = "Update the software",
                type = Server()
            )
        )
        add(
            NoteEntity(
                id = Random.nextInt(31, 50),
                name ="Buy new keyboard",
                description = "Old keyboard if not good",
                type = Keyboard()
            )
        )
        add(
            NoteEntity(
                id = Random.nextInt(31, 50),
                name ="Unblock desktop screen",
                description = "This is important",
                type = Computer()
            )
        )

    }

}