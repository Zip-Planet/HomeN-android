package com.devndev.homen.ui.main.board.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.list.ListScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.boardNav() {
    composable<BottomNavItem.Board> {
        ListScreen()
    }
}
