package com.ai.common_domain.usecase

import com.ai.common_domain.EntityFactory
import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.Computer
import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.Keyboard
import com.ai.common_domain.entities.Server
import com.ai.common_domain.respository.NotesRepository
import io.mockk.coEvery
import org.junit.Test

import io.mockk.mockk
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before

class GetRelatedNotesTest {

    private lateinit var getRelatedNotes : GetRelatedNotes
    private lateinit var repository : NotesRepository

    @Before
    fun setUp() {
        repository = mockk<NotesRepository>()
        getRelatedNotes = (GetRelatedNotes(repository))
    }

    @Test
    fun given_note_of_type_server_return_only_related(): Unit = runTest {

        //given
        val note = EntityFactory.makeNoteEntity(type = Server())
        coEvery { getRelatedNotes.invoke(note) } returns
                ResultWrapper.Success(EntityFactory.getNotEntityList())

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
    fun given_note_of_type_server_return_only_related_force_error(): Unit = runTest {

        //given
        val note = EntityFactory.makeNoteEntity(type = Server())
        coEvery { getRelatedNotes.invoke(note) } returns
                ResultWrapper.Success(EntityFactory.getNotEntityList())

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
    fun given_note_of_type_server_return_only_related_excluding_itself(): Unit = runTest {
        //given
        val note = EntityFactory.makeNoteEntity(type = Server())
        val payloadReturn = EntityFactory.getNotEntityList().toMutableList().apply { add(note) }
        coEvery { getRelatedNotes.invoke(note) } returns
                ResultWrapper.Success(payloadReturn)

        //when
        val result = getRelatedNotes.invoke(note)

        //then
        assert( result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success).data
        assert( data.isNotEmpty() )
        assertNull(data.find { it.id == note.id })
    }

}