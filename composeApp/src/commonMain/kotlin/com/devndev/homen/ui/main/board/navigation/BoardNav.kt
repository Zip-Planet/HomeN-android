package com.devndev.homen.ui.main.board.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.board.main.BoardScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.boardNav() {
    composable<BottomNavItem.Board>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        BoardScreen()
    }
}
