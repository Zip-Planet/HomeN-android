package com.devndev.homen.ui.main.homeintro.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.homeintro.join.CodeEnterScreen
import com.devndev.homen.ui.main.homeintro.joinconfirm.JoinConfirmScreen
import com.devndev.homen.ui.main.homeintro.joindone.JoinDoneScreen
import com.devndev.homen.ui.main.homeintro.main.HomeIntroScreen

fun NavGraphBuilder.homeIntroNav(
    navController: NavHostController,
    onNavToMain: () -> Unit
) {
    composable(HomeIntroRoute.Selection.route) {
         HomeIntroScreen(
             onNavToCreation = { navController.navigate(HomeIntroRoute.Create.route) },
             onNavToCodeEnter = { navController.navigate(HomeIntroRoute.JoinGraph.route) }
         )
    }

    composable(HomeIntroRoute.Create.route) {
        // TODO: CreateHomeScreen 구현 예정
    }

    navigation(
        startDestination = HomeIntroRoute.CodeEnter.route,
        route = HomeIntroRoute.JoinGraph.route,
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        composable(HomeIntroRoute.CodeEnter.route) {
            CodeEnterScreen(
                onNavToConfirm = { navController.navigate(HomeIntroRoute.JoinConfirm.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(HomeIntroRoute.JoinConfirm.route) {
            JoinConfirmScreen(
                onNavToDone = {
                    navController.navigate(HomeIntroRoute.JoinDone.route) {
                        popUpTo(HomeIntroRoute.JoinGraph.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(HomeIntroRoute.JoinDone.route) {
            JoinDoneScreen(
                onNavToHome = onNavToMain
            )
        }
    }
}
