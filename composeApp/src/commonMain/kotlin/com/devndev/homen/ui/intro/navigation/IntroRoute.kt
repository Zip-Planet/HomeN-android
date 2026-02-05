package com.devndev.homen.ui.intro.navigation

sealed class IntroRoute(val route: String) {
    data object Splash : IntroRoute("splash")
    data object Login : IntroRoute("login")
}