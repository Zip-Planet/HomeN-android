package com.devndev.homen.ui.main.present.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.list.PresentScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.presentNav() {
    composable(BottomNavItem.Present.route) {
        PresentScreen()
    }
}
