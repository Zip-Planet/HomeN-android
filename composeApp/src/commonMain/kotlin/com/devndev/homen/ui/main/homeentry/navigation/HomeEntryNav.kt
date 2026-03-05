package com.devndev.homen.ui.main.homeentry.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.homeentry.join.CodeEnterScreen
import com.devndev.homen.ui.main.homeentry.joinconfirm.JoinConfirmScreen
import com.devndev.homen.ui.main.homeentry.joindone.JoinDoneScreen
import com.devndev.homen.ui.main.homeentry.main.HomeEntryScreen

fun NavGraphBuilder.homeEntryNav(
    navController: NavHostController,
    onNavToMain: () -> Unit
) {
    composable(HomeEntryRoute.Selection.route) {
         HomeEntryScreen(
             onNavToCreation = { navController.navigate(HomeEntryRoute.Create.route) },
             onNavToCodeEnter = { navController.navigate(HomeEntryRoute.JoinGraph.route) }
         )
    }

    composable(HomeEntryRoute.Create.route) {
        // TODO: CreateHomeScreen 구현 예정
    }

    navigation(
        startDestination = HomeEntryRoute.CodeEnter.route,
        route = HomeEntryRoute.JoinGraph.route,
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        composable(HomeEntryRoute.CodeEnter.route) {
            CodeEnterScreen(
                onNavToConfirm = { navController.navigate(HomeEntryRoute.JoinConfirm.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(HomeEntryRoute.JoinConfirm.route) {
            JoinConfirmScreen(
                onNavToDone = {
                    navController.navigate(HomeEntryRoute.JoinDone.route) {
                        popUpTo(HomeEntryRoute.JoinGraph.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(HomeEntryRoute.JoinDone.route) {
            JoinDoneScreen(
                onNavToHome = onNavToMain
            )
        }
    }
}
