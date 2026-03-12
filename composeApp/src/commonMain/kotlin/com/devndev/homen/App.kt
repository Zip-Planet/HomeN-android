package com.devndev.homen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.intro.navigation.IntroNav
import com.devndev.homen.ui.main.navigation.MainNav
import com.devndev.homen.ui.navigation.AppRoute
import com.devndev.homen.ui.theme.HomeNTheme

@Composable
fun HomeNApp() {
    HomeNTheme {
        val rootNavController = rememberNavController()

        NavHost(
            navController = rootNavController,
            startDestination = AppRoute.Intro
        ) {
            composable<AppRoute.Intro>(
                enterTransition = NavTransitions.enterTransition,
                exitTransition = NavTransitions.exitTransition,
                popEnterTransition = NavTransitions.popEnterTransition,
                popExitTransition = NavTransitions.popExitTransition
            ) {
                IntroNav(onNavToMain = {
                    rootNavController.navigate(AppRoute.Main) {
                        popUpTo<AppRoute.Intro> { inclusive = true }
                    }
                })
            }
            composable<AppRoute.Main>(
                enterTransition = NavTransitions.enterTransition,
                exitTransition = NavTransitions.exitTransition,
                popEnterTransition = NavTransitions.popEnterTransition,
                popExitTransition = NavTransitions.popExitTransition
            ) {
                MainNav()
            }
        }
    }
}
