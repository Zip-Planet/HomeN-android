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
    composable<HomeIntroRoute.Selection> {
         HomeIntroScreen(
             onNavToCreation = { navController.navigate(HomeIntroRoute.Create) },
             onNavToCodeEnter = { navController.navigate(HomeIntroRoute.JoinGraph) }
         )
    }

    composable<HomeIntroRoute.Create> {
        // TODO: CreateHomeScreen 구현 예정
    }

    navigation<HomeIntroRoute.JoinGraph>(
        startDestination = HomeIntroRoute.CodeEnter,
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        composable<HomeIntroRoute.CodeEnter> {
            CodeEnterScreen(
                onNavToConfirm = { navController.navigate(HomeIntroRoute.JoinConfirm) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<HomeIntroRoute.JoinConfirm> {
            JoinConfirmScreen(
                onNavToDone = {
                    navController.navigate(HomeIntroRoute.JoinDone) {
                        popUpTo<HomeIntroRoute.JoinGraph> { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<HomeIntroRoute.JoinDone> {
            JoinDoneScreen(
                onNavToHome = onNavToMain
            )
        }
    }
}
