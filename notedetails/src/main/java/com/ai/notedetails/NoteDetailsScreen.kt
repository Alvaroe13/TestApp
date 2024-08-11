package com.ai.notedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import com.ai.common.components.TopBar
import com.ai.common.theme.TestAppTheme
import com.ai.common.utils.HandleEffects
import com.ai.common_domain.entities.NoteType
import com.ai.notedetails.components.TextInputField


@Composable
fun NoteDetailsScreen(
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state by viewModel.screenState.collectAsState()

    HandleEffects(effectFlow = viewModel.effect) { effect ->
        when(effect) {
            NoteDetailsScreenEffect.GoBack -> navController.navigateUp()
        }
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
                title = {
                    Text(
                        text = stringResource(
                            id = if (state.noteState.name == NoteState.INSERT.name) R.string.new_object else R.string.edit_object
                        )
                    )
                        },
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(end = 8.dp, bottom = 8.dp)
    ) {
        Column( modifier = Modifier.padding(top = 8.dp)) {
            TextInputField(
                header = stringResource(id = R.string.object_name),
                value = state.note.name,
                onValueChange = { action(NoteDetailsScreenActions.OnNameChanged(it)) }
            )
            TextInputField(
                header = stringResource(id = R.string.object_description),
                value = state.note.description,
                onValueChange = { action(NoteDetailsScreenActions.OnDescriptionChanged(it)) }
            )
            TextInputField(
                header = stringResource(id = R.string.object_type),
                value = if (state.note.type == NoteType.IDLE) "" else state.note.type.name,
                onValueChange = { }
            )

        }

        LazyColumn(modifier = Modifier.wrapContentSize()) {
            // for related notes
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
            onClick = { action(NoteDetailsScreenActions.SaveNote) },
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
fun GreetingPreview() {
    TestAppTheme {
        ValidContent(state = NoteDetailsScreenState(), action =  {} )
    }
}