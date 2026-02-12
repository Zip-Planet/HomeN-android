package com.devndev.homen.ui.intro.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.intro.login.LoginScreen
import com.devndev.homen.ui.intro.register.RegisterScreen
import com.devndev.homen.ui.intro.splash.SplashScreen
import com.devndev.homen.ui.theme.BackgroundGray

@Composable
fun IntroNav(
    onNaveToMain: () -> Unit
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
                    onNaveToMain()
                } else {
                    introNavController.navigate(IntroRoute.Login.route) {
                        popUpTo(IntroRoute.Splash.route) { inclusive = true }
                    }
                }
            }
        }

        composable(IntroRoute.Login.route) {
            LoginScreen(
                onNavToMain = onNaveToMain,
                onNavToRegister = {
                    introNavController.navigate(IntroRoute.Register.route)
                }
            )
        }

        composable(IntroRoute.Register.route) {
            RegisterScreen(
                onNavBack = { introNavController.popBackStack() }
            )
        }
    }
}
