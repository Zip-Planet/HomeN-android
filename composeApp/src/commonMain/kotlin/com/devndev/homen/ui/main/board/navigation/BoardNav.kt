package com.devndev.homen.ui.main.board.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.board.main.BoardScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.boardNav(
    navController: NavController,
    paddingValues: PaddingValues
) {
    composable<BottomNavItem.Board>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        BoardScreen(
            paddingValues = paddingValues,
            onNavToReward = {
                navController.navigate(BottomNavItem.Reward) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onNavToAssignment = {
                navController.navigate(BottomNavItem.Assignment(true)) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
