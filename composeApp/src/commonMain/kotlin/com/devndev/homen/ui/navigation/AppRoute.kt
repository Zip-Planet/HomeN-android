package com.devndev.homen.ui.navigation

sealed class AppRoute(val route: String) {
    data object Intro : AppRoute("Intro")
    data object Main : AppRoute("main")
}
