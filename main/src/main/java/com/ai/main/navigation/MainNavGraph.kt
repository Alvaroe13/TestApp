package com.ai.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ai.common.navigation.ScreenDestinations
import com.ai.notedetails.NoteDetailsScreen
import com.ai.notelist.NoteListScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController,) {
    composable(route = ScreenDestinations.NoteListScreen.route) {
        NoteListScreen(navController = navController)
    }
    composable(route = ScreenDestinations.NoteDetailsScreen.route) {
        NoteDetailsScreen()
    }
}