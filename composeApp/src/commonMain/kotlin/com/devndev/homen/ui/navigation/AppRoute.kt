package com.devndev.homen.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object Intro : AppRoute
    
    @Serializable
    data object Main : AppRoute
}
