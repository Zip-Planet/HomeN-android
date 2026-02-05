package com.devndev.homen.ui.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.home.HomeScreen

fun NavGraphBuilder.homeNav() {
    composable(BottomNavItem.Home.route) {
        HomeScreen()
    }
}
