package com.devndev.homen.ui.main.homeintro.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.homeintro.create.CreateHomeFlowScreen
import com.devndev.homen.ui.main.homeintro.create.CreateOnboardingScreen
import com.devndev.homen.ui.main.homeintro.join.CodeEnterScreen
import com.devndev.homen.ui.main.homeintro.joinconfirm.JoinConfirmScreen
import com.devndev.homen.ui.main.homeintro.joindone.JoinDoneScreen
import com.devndev.homen.ui.main.homeintro.main.HomeIntroScreen

fun NavGraphBuilder.homeIntroNav(
    navController: NavHostController,
    onNavToMain: () -> Unit,
    onNavToIntro: () -> Unit
) {
    composable<HomeIntroRoute.Selection>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        HomeIntroScreen(
            onNavToCreation = { navController.navigate(HomeIntroRoute.CreateOnboarding) },
            onNavToCodeEnter = { navController.navigate(HomeIntroRoute.JoinGraph) },
            onNavToIntro = { onNavToIntro() }
        )
    }

    composable<HomeIntroRoute.CreateOnboarding>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        CreateOnboardingScreen(
            onNextClick = { navController.navigate(HomeIntroRoute.CreateGraph) },
            onBackClick = { navController.popBackStack() }
        )
    }

    composable<HomeIntroRoute.CreateGraph>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        CreateHomeFlowScreen(
            onNavToMain = { onNavToMain() },
            onExitFlow = { navController.popBackStack() }
        )
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
                onNavToConfirm = { code ->
                    navController.navigate(HomeIntroRoute.JoinConfirm(code))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<HomeIntroRoute.JoinConfirm> { backStackEntry ->
            val route: HomeIntroRoute.JoinConfirm = backStackEntry.toRoute()
            JoinConfirmScreen(
                code = route.code,
                onNavToDone = { homeName, homeIcon ->
                    navController.navigate(HomeIntroRoute.JoinDone(homeName, homeIcon)) {
                        popUpTo<HomeIntroRoute.JoinGraph> { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<HomeIntroRoute.JoinDone> { backStackEntry ->
            val route: HomeIntroRoute.JoinDone = backStackEntry.toRoute()
            JoinDoneScreen(
                homeName = route.homeName,
                homeIcon = route.homeIcon,
                onNavToHome = onNavToMain
            )
        }
    }
}
