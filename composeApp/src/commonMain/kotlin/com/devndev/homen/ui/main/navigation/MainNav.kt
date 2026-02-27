package com.devndev.homen.ui.main.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.MainBottomBar
import com.devndev.homen.ui.main.assignment.navigation.assignmentNav
import com.devndev.homen.ui.main.board.navigation.boardNav
import com.devndev.homen.ui.main.home.navigation.homeNav
import com.devndev.homen.ui.main.mypage.navigation.myPageNav
import com.devndev.homen.ui.main.present.navigation.presentNav

@Composable
fun MainNav() {
    val mainNavController = rememberNavController()
    // 공통 화면 틀인 HomeNScreen 사용
    HomeNScreen(
        bottomBar = {
            MainBottomBar(navController = mainNavController)
        }
    ) { paddingValues ->
        NavHost(
            navController = mainNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            homeNav()
            boardNav()
            assignmentNav()
            presentNav()
            myPageNav()
        }
    }
}
