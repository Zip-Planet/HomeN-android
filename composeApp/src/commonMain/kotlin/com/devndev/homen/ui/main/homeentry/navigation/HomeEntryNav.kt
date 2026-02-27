package com.devndev.homen.ui.main.homeentry.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeEntryNav(
    navController: NavHostController,
    onNavToMain: () -> Unit
) {
    // 1. 집 만들기/입장 선택 화면
    composable(HomeEntryRoute.Selection.route) {
        // TODO: HomeEntryScreen 구현 예정
        // HomeEntryScreen(
        //     onNavToCreate = { navController.navigate(HomeEntryRoute.Create.route) },
        //     onNavToJoin = { navController.navigate(HomeEntryRoute.Join.route) }
        // )
    }

    // 2. 집 새로 만들기 화면
    composable(HomeEntryRoute.Create.route) {
        // TODO: CreateHomeScreen 구현 예정
    }

    // 3. 초대 코드로 입장하기 화면
    composable(HomeEntryRoute.Join.route) {
        // TODO: JoinHomeScreen 구현 예정
    }
}
