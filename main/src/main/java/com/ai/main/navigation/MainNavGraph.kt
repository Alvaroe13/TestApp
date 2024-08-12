package com.ai.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ai.common.navigation.ArgumentKeyConstants.NOTE_ID_KEY
import com.ai.common.navigation.ScreenDestinations
import com.ai.notedetails.NoteDetailsScreen
import com.ai.notelist.NoteListScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController,) {
    composable(route = ScreenDestinations.NoteListScreen.route) {
        NoteListScreen(navController = navController)
    }
    composable(
        route = ScreenDestinations.NoteDetailsScreen.route + "?$NOTE_ID_KEY={$NOTE_ID_KEY}",
        arguments = listOf(
            navArgument(name = NOTE_ID_KEY) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) {
        NoteDetailsScreen(navController = navController)
    }
}