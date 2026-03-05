package com.devndev.homen.ui.intro.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.intro.login.LoginScreen
import com.devndev.homen.ui.intro.register.RegisterScreen
import com.devndev.homen.ui.intro.splash.SplashScreen

@Composable
fun IntroNav(
    onNavToMain: () -> Unit
) {
    val introNavController = rememberNavController()

    NavHost(
        navController = introNavController,
        startDestination = IntroRoute.Splash.route,
        enterTransition = { androidx.compose.animation.EnterTransition.None },
        exitTransition = { androidx.compose.animation.ExitTransition.None },
        popEnterTransition = { androidx.compose.animation.EnterTransition.None },
        popExitTransition = { androidx.compose.animation.ExitTransition.None }
    ) {
        composable(IntroRoute.Splash.route) {
            SplashScreen { isValidToken ->
                if (isValidToken) {
                    onNavToMain()
                } else {
                    introNavController.navigate(IntroRoute.Login.route) {
                        popUpTo(IntroRoute.Splash.route) { inclusive = true }
                    }
                }
            }
        }

        composable(IntroRoute.Login.route) {
            LoginScreen(
                onNavToMain = onNavToMain,
                onNavToRegister = {
                    introNavController.navigate(IntroRoute.Register.route)
                }
            )
        }

        composable(
            IntroRoute.Register.route,
            enterTransition = NavTransitions.enterTransition,
            exitTransition = NavTransitions.exitTransition,
            popEnterTransition = NavTransitions.popEnterTransition,
            popExitTransition = NavTransitions.popExitTransition
        ) {
            RegisterScreen(
                onNavToMain = onNavToMain,
                onNavBack = { introNavController.popBackStack() }
            )
        }
    }
}
