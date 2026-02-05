package com.devndev.homen.ui.intro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.intro.login.LoginScreen
import com.devndev.homen.ui.intro.splash.SplashScreen
import com.devndev.homen.ui.navigation.AppRoute
import com.devndev.homen.ui.intro.navigation.IntroRoute

@Composable
fun IntroNav(
    onNavigateToMain: () -> Unit
) {
    val introNavController = rememberNavController()

    NavHost(
        navController = introNavController,
        startDestination = IntroRoute.Splash.route
    ) {
        composable(IntroRoute.Splash.route) {
            SplashScreen { isValidToken ->
                if (isValidToken) {
                    onNavigateToMain()
                } else {
                    introNavController.navigate(IntroRoute.Login.route) {
                        popUpTo(IntroRoute.Splash.route) { inclusive = true }
                    }
                }
            }
        }
        composable(IntroRoute.Login.route) {
            LoginScreen(onNavigateToMain = onNavigateToMain)
        }
    }
}
