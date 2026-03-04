package com.devndev.homen.ui.main.homeentry.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.homeentry.join.CodeEnterScreen
import com.devndev.homen.ui.main.homeentry.main.HomeEntryScreen

fun NavGraphBuilder.homeEntryNav(
    navController: NavHostController,
    onNavToMain: () -> Unit
) {
    composable(HomeEntryRoute.Selection.route) {
         HomeEntryScreen(
             onNavToCreation = { navController.navigate(HomeEntryRoute.Create.route) },
             onNavToJoin = { navController.navigate(HomeEntryRoute.Join.route) }
         )
    }

    composable(HomeEntryRoute.Create.route) {
        // TODO: CreateHomeScreen 구현 예정
    }

    composable(HomeEntryRoute.Join.route) {
        CodeEnterScreen(
            onNavToMain = onNavToMain,
            onBackClick = { navController.popBackStack() }
        )
    }
}
