package com.ai.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ai.common.navigation.ScreenDestinations

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    startDestination: ScreenDestinations = ScreenDestinations.NoteListScreen,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        builder = { mainNavGraph(navController) }
    )
}