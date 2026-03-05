package com.devndev.homen.ui.main.homeentry.navigation

/**
 * 집 입장(Home Entry) 과정 전용 경로
 */
sealed class HomeEntryRoute(val route: String) {
    data object Selection : HomeEntryRoute("entry_selection")
    data object Create : HomeEntryRoute("entry_create")
    
    data object JoinGraph : HomeEntryRoute("entry_join_graph")
    data object CodeEnter : HomeEntryRoute("entry_code_enter")
    data object JoinConfirm : HomeEntryRoute("entry_join_confirm")
    data object JoinDone : HomeEntryRoute("entry_join_done")
}
