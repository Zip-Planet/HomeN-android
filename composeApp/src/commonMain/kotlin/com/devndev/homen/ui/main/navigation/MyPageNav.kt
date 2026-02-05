package com.devndev.homen.ui.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.mypage.MyPageScreen

fun NavGraphBuilder.myPageNav() {
    composable(BottomNavItem.MyPage.route) {
        MyPageScreen()
    }
}
