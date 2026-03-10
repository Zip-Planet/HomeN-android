package com.devndev.homen.ui.main.homeintro.navigation

/**
 * 집 입장(Home Intro) 과정 전용 경로
 */
sealed class HomeIntroRoute(val route: String) {
    data object Selection : HomeIntroRoute("Intro_selection")
    data object Create : HomeIntroRoute("Intro_create")
    
    data object JoinGraph : HomeIntroRoute("Intro_join_graph")
    data object CodeEnter : HomeIntroRoute("Intro_code_enter")
    data object JoinConfirm : HomeIntroRoute("Intro_join_confirm")
    data object JoinDone : HomeIntroRoute("entry_join_done")
}
