package com.devndev.homen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.intro.navigation.IntroNav
import com.devndev.homen.ui.main.navigation.MainNav
import com.devndev.homen.ui.navigation.AppRoute

@Composable
fun HomeNApp() {
    MaterialTheme {
        val rootNavController = rememberNavController()

        NavHost(
            navController = rootNavController,
            startDestination = AppRoute.Intro.route
        ) {
            composable(AppRoute.Intro.route) {
                IntroNav(onNavigateToMain = {
                    rootNavController.navigate("main") {
                        popUpTo("intro") { inclusive = true }
                    }
                })
            }
            composable(AppRoute.Main.route) {
                MainNav()
            }
        }
    }
}
