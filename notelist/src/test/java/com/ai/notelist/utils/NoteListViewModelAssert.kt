package com.ai.notelist.utils

import com.ai.notelist.NoteListViewModel
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue

fun NoteListViewModel.verifyIsLoading() : NoteListViewModel {
    assertTrue(this.screenState.value.isLoading)
    return this
}

fun NoteListViewModel.assertIsNotLoading() : NoteListViewModel {
    assertFalse(this.screenState.value.isLoading)
    return this
}

fun NoteListViewModel.assertNotesAreEmpty() : NoteListViewModel {
    assertTrue(this.screenState.value.notes.isEmpty())
    return this
}

fun NoteListViewModel.assertErrorIsTrue() : NoteListViewModel {
    assertTrue(this.screenState.value.error)
    return this
}

fun NoteListViewModel.assertErrorIsFalse() : NoteListViewModel {
    assertFalse(this.screenState.value.error)
    return this
}

fun NoteListViewModel.assertShowDeletionTrue() : NoteListViewModel {
    assertTrue(this.screenState.value.showDeleteOption)
    return this
}

fun NoteListViewModel.assertShowDeletionFalse() : NoteListViewModel {
    assertFalse(this.screenState.value.showDeleteOption)
    return this
}

fun NoteListViewModel.assertNoteForDeletionSelected() : NoteListViewModel {
    assertNotNull(this.screenState.value.noteForDeletion)
    return this
}

fun NoteListViewModel.assertNoteForDeletionNotSelected() : NoteListViewModel {
    assertNull(this.screenState.value.noteForDeletion)
    return this
}