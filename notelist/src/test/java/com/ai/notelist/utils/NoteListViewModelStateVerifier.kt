package com.ai.notelist.utils

import com.ai.notelist.NoteListViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue

class NoteListViewModelStateVerifier private constructor() {

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

         fun verifyShowDeletionTrue() : Builder {
             assertTrue(viewModel.screenState.value.showDeleteOption)
             return this
         }

         fun verifyShowDeletionFalse() : Builder {
             assertFalse(viewModel.screenState.value.showDeleteOption)
             return this
         }

         fun verifyNoteForDeletionSelected() : Builder {
             assertNotNull(viewModel.screenState.value.noteForDeletion)
             return this
         }

         fun verifyNoteForDeletionNotSelected() : Builder {
             assertNull(viewModel.screenState.value.noteForDeletion)
             return this
         }
     }
}