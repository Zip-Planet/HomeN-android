package com.devndev.homen.ui.main.homeintro.navigation

import kotlinx.serialization.Serializable

/**
 * 집 입장(Home Entry) 과정 전용 경로
 */
@Serializable
sealed interface HomeIntroRoute {
    @Serializable
    data object Selection : HomeIntroRoute

    @Serializable
    data object CreateGraph : HomeIntroRoute
    
    @Serializable
    data object CreateOnboarding : HomeIntroRoute
    
    @Serializable
    data object CreateProfile : HomeIntroRoute
    @Serializable
    data object CreatePack : HomeIntroRoute

    @Serializable
    data object PackPreview: HomeIntroRoute

    @Serializable
    data object CreateReward : HomeIntroRoute

    @Serializable
    data object CreateDone: HomeIntroRoute
    
    @Serializable
    data object JoinGraph : HomeIntroRoute
    @Serializable
    data object CodeEnter : HomeIntroRoute
    @Serializable
    data class JoinConfirm(val code: String) : HomeIntroRoute
    @Serializable
    data class JoinDone(val homeName: String, val homeIcon: Int) : HomeIntroRoute
}
