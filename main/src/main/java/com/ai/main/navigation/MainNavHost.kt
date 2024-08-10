package com.ai.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    startDestination: ScreenDestinations = ScreenDestinations.NoteDetailsScreen,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        builder = { mainNavGraph() }
    )
}