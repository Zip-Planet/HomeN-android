package com.devndev.homen.ui.main.mypage.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.mypage.main.MyPageScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.myPageNav(
    navController: NavController,
    paddingValues: PaddingValues,
    onNavToIntro: () -> Unit
) {
    composable<BottomNavItem.MyPage>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        MyPageScreen(
            onNavToLogin = onNavToIntro
        )
    }
}
