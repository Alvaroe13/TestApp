package com.ai.notelist

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetRelatedNotes
import io.mockk.coEvery
import org.junit.Test

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking

class GetRelatedNotesTest {

    lateinit var getRelatedNotes : GetRelatedNotes

   /* @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.getRelatedNotes = (GetRelatedNotes(repository))
    }*/

    @Test
    fun addition_isCorrect(): Unit = runBlocking {
        //assertEquals(4, 2 + 2)

        val repository = mockk<NotesRepository>()
        getRelatedNotes = GetRelatedNotes(repository)

        //given
         coEvery { getRelatedNotes.invoke(EntityFactory.getNoteEntity()) } returns
                ResultWrapper.Success(EntityFactory.getNotEntityList())

        //when
        val result = getRelatedNotes.invoke(EntityFactory.getNoteEntity())

        //then
        assert( result is ResultWrapper.Success)
        //assert( (result as ResultWrapper.Success).data.isNotEmpty() )

    }

}