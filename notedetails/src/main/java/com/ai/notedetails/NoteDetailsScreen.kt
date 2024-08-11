package com.ai.notedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ai.common.components.NoteCard
import com.ai.common.components.TopBar
import com.ai.common.theme.TestAppTheme
import com.ai.common.utils.HandleEffects


@Composable
fun NoteDetailsScreen(
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state by viewModel.screenState.collectAsState()

    HandleEffects(effectFlow = viewModel.effect) {

    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        UiContent(state = state, action = viewModel::setAction)
    }
}

@Composable
private fun UiContent(
    modifier : Modifier = Modifier,
    state: NoteDetailsScreenState,
    action: (action: NoteDetailsScreenActions) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = { Text(text = stringResource(id = R.string.edit_object)) },
                actions = { }
            )
        }
    ) { paddingValues ->
        if (state.error) {
            ErrorContent(modifier.padding(paddingValues))
        } else {
            if (state.isLoading) LoadingContent(modifier.padding(paddingValues))
            else ValidContent(
                modifier = modifier.padding(paddingValues),
                state = state,
                action = action
            )
        }
    }
}

@Composable
private fun ValidContent(
    modifier : Modifier = Modifier,
    state: NoteDetailsScreenState,
    action: (NoteDetailsScreenActions) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
           /* items(state.notes) { note ->
                NoteCard(
                    title = note.title,
                    description = note.description
                ) {
                    action(NoteListScreenActions.OnNoteSelectedClick(note))
                }
            }*/
        }
    }
}

@Composable
fun LoadingContent(modifier : Modifier = Modifier) {
    //Could be a generic one
}


@Composable
fun ErrorContent(modifier : Modifier = Modifier) {
    //Could be a generic one
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppTheme {
        ValidContent(state = NoteDetailsScreenState(), action =  {} )
    }
}