package com.ai.notelist.utils

import com.ai.notelist.NoteListViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue

class ViewModelStateVerifier private constructor() {

     class Builder(private val viewModel: NoteListViewModel) {

         fun verifyIsLoading() : Builder {
             assertTrue(viewModel.screenState.value.isLoading)
             return this
         }

         fun verifyIsNotLoading() : Builder {
             assertFalse(viewModel.screenState.value.isLoading)
             return this
         }

         fun verifyNotesAreEmpty() : Builder {
             assertTrue(viewModel.screenState.value.notes.isEmpty())
             return this
         }

         fun verifyErrorIsTrue() : Builder {
             assertTrue(viewModel.screenState.value.error)
             return this
         }

         fun verifyErrorIsFalse() : Builder {
             assertFalse(viewModel.screenState.value.error)
             return this
         }

     }
}