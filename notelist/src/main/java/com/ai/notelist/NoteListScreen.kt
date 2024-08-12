package com.ai.notelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ai.common.components.NoteCard
import com.ai.common.components.NoteCardInputField
import com.ai.common.components.TopBar
import com.ai.common.navigation.ArgumentKeyConstants.NOTE_ID_KEY
import com.ai.common.navigation.ArgumentKeyConstants.REFRESH_KEY
import com.ai.common.navigation.ScreenDestinations
import com.ai.common.theme.TestAppTheme
import com.ai.common.utils.HandleEffects
import com.ai.common_domain.entities.NoteEntity
import com.ai.notelist.components.DeletionDialog

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state by viewModel.screenState.collectAsState()

    HandleEffects(effectFlow = viewModel.effect) { effect ->
        when(effect) {
            is NoteListScreenEffects.CreateNewNoteClicked -> {
                navController.navigate(ScreenDestinations.NoteDetailsScreen.route)
            }
            is NoteListScreenEffects.NoteSelected -> {
                navController.navigate("${ScreenDestinations.NoteDetailsScreen.route}?$NOTE_ID_KEY=${effect.noteId}")
            }
        }
    }

    navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(REFRESH_KEY)?.let {
        if (it) {
            viewModel.setAction(action = NoteListScreenActions.LoadNotes)
            navController.currentBackStackEntry?.savedStateHandle?.set(REFRESH_KEY, false)
        }
    }

    UiContent(state = state, action = viewModel::setAction)
}

@Composable
private fun UiContent(
    modifier : Modifier = Modifier,
    state: NoteListScreenState,
    action: (action: NoteListScreenActions) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            topBar = {
                TopBar(
                    title = { Text(text = stringResource(id = R.string.`object`)) },
                    actions = { }
                )
            },
            content = { paddingValues ->
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
        )

    }
}
@Composable
private fun ValidContent(
    modifier : Modifier = Modifier,
    state: NoteListScreenState,
    action: (NoteListScreenActions) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.search_bar_placeholder),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            NoteCardInputField(
                header = "",
                value = state.searchQuery ?: "",
                onValueChange = { action(NoteListScreenActions.OnSearchQueryChanged(it)) }
            )
            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteCard(
                        title = note.name,
                        description = note.description,
                        onNoteTapped = { action(NoteListScreenActions.OnNoteSelectedClick(note)) },
                        onLongPressed = { action(NoteListScreenActions.OnNoteLongPressed(note) ) }
                    )
                }
            }
        }
    }

    if (state.showDeleteOption) {
        DeletionDialog(
            onConfirm = { action(NoteListScreenActions.OnNoteDeletionConfirmed( checkNotNull(state.noteForDeletion) )) },
            onCancel = { action(NoteListScreenActions.OnNoteDeletionCancelled) }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(end = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            modifier = Modifier
                .width(55.dp)
                .height(55.dp),
            onClick = { action(NoteListScreenActions.OnNewNoteClick) },
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Add, null)
        }
    }
}

@Composable
fun LoadingContent(modifier : Modifier = Modifier) {
    //Could be a proper and generic screen
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading")
    }
}


@Composable
fun ErrorContent(modifier : Modifier = Modifier) {
    //Could be a proper and generic screen
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error")
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenPreview() {
    TestAppTheme {
        ValidContent(state = NoteListScreenState(notes = mockNotes()), action =  {} )
    }
}

private fun mockNotes(): List<NoteEntity> =
    buildList {
        add(
            NoteEntity(
                id = 1,
                name = "This is the title",
                description = "This is a description"
            )
        )
        add(
            NoteEntity(
                id = 1,
                name = "This is the title",
                description = "This is a description"
            )
        )
        add(
            NoteEntity(
                id = 1,
                name = "This is the title",
                description = "This is a description"
            )
        )
        add(
            NoteEntity(
                id = 1,
                name = "This is the title",
                description = "This is a description"
            )
        )
    }

@Preview(showBackground = true)
@Composable
private fun NoteListScreenLoadingPreview() {
    TestAppTheme {
        LoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteListScreenErrorPreview() {
    TestAppTheme {
        ErrorContent()
    }
}