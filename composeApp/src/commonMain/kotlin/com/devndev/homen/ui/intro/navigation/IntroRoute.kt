package com.devndev.homen.ui.intro.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface IntroRoute {
    @Serializable
    data object Splash : IntroRoute
    
    @Serializable
    data object Login : IntroRoute
    
    @Serializable
    data object Register : IntroRoute
}
