package com.ai.common_domain


import com.ai.common_domain.entities.Computer
import com.ai.common_domain.entities.Desk
import com.ai.common_domain.entities.Human
import com.ai.common_domain.entities.Keyboard
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.entities.Server
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetNotesByQuery
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.testng.Assert.assertEquals
import org.testng.Assert.assertNotEquals

class GetNotesByQueryTest {

    private lateinit var getNotesByQueryTest: GetNotesByQuery
    private lateinit var repository: NotesRepository

    @Before
    fun setUp() {
        repository = mockk()
        getNotesByQueryTest = GetNotesByQuery(repository)
    }

    @Test
    fun `given empty field then return all notes`() : Unit = runTest {
        //given
        val query = ""
        val fakeNotes = getNotEntityList()
        coEvery { repository.getAllNotes() } returns ResultWrapper.Success(fakeNotes)

        //when
        val result = getNotesByQueryTest(query)

        //then
        assert(result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success ).data
        assertEquals(data.size, fakeNotes.size)
    }

    @Test
    fun `given input field is clean then return all notes`() : Unit = runTest {
        //given
        val query = "clean"
        val fakeNotes = getNotEntityList()
        coEvery { repository.getAllNotes() } returns ResultWrapper.Success(fakeNotes)

        //when
        val result = getNotesByQueryTest(query)

        //then
        assert(result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success ).data
        assertNotEquals(data.size, fakeNotes.size)
        assertEquals( data.size , 1)
        assertEquals( data.first().id , 1)
        assert( data.first().type is Desk )
    }

    @Test
    fun `given input field is soda then return no notes`() : Unit = runTest {
        //given
        val query = "soda"
        val fakeNotes = getNotEntityList()
        coEvery { repository.getAllNotes() } returns ResultWrapper.Success(fakeNotes)

        //when
        val result = getNotesByQueryTest(query)

        //then
        assert(result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success ).data
        assert(data.isEmpty())
    }


    @Test
    fun `given input field is soda and there is no notes stored then return no notes`() : Unit = runTest {
        //given
        val query = "soda"
        val fakeNotes = getNotEntityList()
        coEvery { repository.getAllNotes() } returns ResultWrapper.Success(listOf())


        //when
        val result = getNotesByQueryTest(query)

        //then
        assert(result is ResultWrapper.Success)
        val data = (result as ResultWrapper.Success ).data
        assert(data.isEmpty())
    }


    private fun getNotEntityList() = buildList {
        add(
            NoteEntity(
                id = 0,
                name ="Anything",
                description = "Any",
                type = Human()
            )
        )
        add(
            NoteEntity(
                id = 1,
                name ="Clean the desk",
                description = "Tidy up the room",
                type = Desk()
            )
        )
        add(
            NoteEntity(
                id = 2,
                name ="Update the software",
                description = "Update the software",
                type = Server()
            )
        )
        add(
            NoteEntity(
                id = 3,
                name ="Buy new keyboard",
                description = "Old keyboard if not good",
                type = Keyboard()
            )
        )
        add(
            NoteEntity(
                id = 4,
                name ="Unblock desktop screen",
                description = "This is important",
                type = Computer()
            )
        )

    }

}