package com.devndev.homen.ui.main.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.home.HomeScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.homeNav() {
    composable(BottomNavItem.Home.route) {
        HomeScreen()
    }
}
