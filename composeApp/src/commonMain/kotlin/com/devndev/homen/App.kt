package com.devndev.homen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            startDestination = AppRoute.Intro.route
        ) {
            composable(AppRoute.Intro.route) {
                IntroNav(onNaveToMain = {
                    rootNavController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Intro.route) { inclusive = true }
                    }
                })
            }
            composable(AppRoute.Main.route) {
                MainNav()
            }
        }
    }
}
