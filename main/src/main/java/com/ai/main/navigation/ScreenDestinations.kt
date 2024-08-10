package com.ai.main.navigation

sealed class ScreenDestinations(val route: String) {
    object NoteListScreen: ScreenDestinations("note_list_screen")
    object NoteDetailsScreen: ScreenDestinations("note_detail_screen")
}