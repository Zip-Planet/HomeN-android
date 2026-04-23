package com.devndev.homen.ui.intro.navigation

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
        startDestination = IntroRoute.Splash,
        enterTransition = { androidx.compose.animation.EnterTransition.None },
        exitTransition = { androidx.compose.animation.ExitTransition.None },
        popEnterTransition = { androidx.compose.animation.EnterTransition.None },
        popExitTransition = { androidx.compose.animation.ExitTransition.None }
    ) {
        composable<IntroRoute.Splash> {
            SplashScreen(
                onNavToMain = onNavToMain,
                onNavToLogin = {
                    introNavController.navigate(IntroRoute.Login) {
                        popUpTo<IntroRoute.Splash> { inclusive = true }
                    }
                }
            )
        }

        composable<IntroRoute.Login> {
            LoginScreen(
                onNavToMain = onNavToMain,
                onNavToRegister = {
                    introNavController.navigate(IntroRoute.Register)
                }
            )
        }

        composable<IntroRoute.Register>(
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
