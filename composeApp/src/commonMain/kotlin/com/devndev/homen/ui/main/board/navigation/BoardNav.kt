package com.devndev.homen.ui.main.board.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.component.NavTransitions.enterTransition
import com.devndev.homen.ui.component.NavTransitions.exitTransition
import com.devndev.homen.ui.component.NavTransitions.popEnterTransition
import com.devndev.homen.ui.component.NavTransitions.popExitTransition
import com.devndev.homen.ui.main.board.exchange.BoardExchangeScreen
import com.devndev.homen.ui.main.board.help.BoardHelpScreen
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
            },
            onNavToHelp = {
                navController.navigate(BoardRoute.BoardHelp)
            },
            onNavToExchange = {
                navController.navigate(BoardRoute.BoardExchange)
            }
        )
    }

    composable<BoardRoute.BoardHelp>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        BoardHelpScreen(
            onNavBack = {
                navController.popBackStack()
            }
        )
    }

    composable<BoardRoute.BoardExchange>(
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        BoardExchangeScreen(
            onNavBack = {
                navController.popBackStack()
            }
        )
    }
}
