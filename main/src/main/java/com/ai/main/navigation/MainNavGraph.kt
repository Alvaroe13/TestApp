package com.ai.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ai.notelist.NoteListScreen

fun NavGraphBuilder.mainNavGraph() {
    composable(route = ScreenDestinations.NoteListScreen.route) {
        NoteListScreen()
    }
    composable(route = ScreenDestinations.NoteDetailsScreen.route) {
        //to be implemented
    }
}