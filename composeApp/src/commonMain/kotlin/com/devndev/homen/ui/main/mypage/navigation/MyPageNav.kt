package com.devndev.homen.ui.main.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.mypage.MyPageScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.myPageNav() {
    composable(BottomNavItem.MyPage.route) {
        MyPageScreen()
    }
}
