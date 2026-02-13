package com.devndev.homen.ui.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.list.ListScreen

fun NavGraphBuilder.boardNav() {
    composable(BottomNavItem.Board.route) {
        ListScreen()
    }
}
