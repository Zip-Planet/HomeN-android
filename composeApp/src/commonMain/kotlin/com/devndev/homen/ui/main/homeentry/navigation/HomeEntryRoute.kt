package com.devndev.homen.ui.main.homeentry.navigation

/**
 * 집 입장(Home Entry) 과정 전용 경로
 */
sealed class HomeEntryRoute(val route: String) {
    data object Selection : HomeEntryRoute("entry_selection") // 만들기/입장 선택 메인
    data object Create : HomeEntryRoute("entry_create")       // 집 새로 만들기
    data object Join : HomeEntryRoute("entry_join")           // 초대 코드로 입장하기
}
