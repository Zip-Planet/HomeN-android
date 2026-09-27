package com.devndev.homen.ui.main.mypage.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.mypage.edit.ProfileSettingScreen
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
            onNavToLogin = onNavToIntro,
            onNavToProfileSetting = { nickname, avatarId ->
                navController.navigate(MyPageRoute.ProfileSetting(nickname, avatarId))
            }
        )
    }

    composable<MyPageRoute.ProfileSetting>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) { backStackEntry ->
        val route: MyPageRoute.ProfileSetting = backStackEntry.toRoute()
        ProfileSettingScreen(
            initialNickname = route.nickname,
            initialAvatarId = route.avatarId,
            onNavBack = {
                navController.popBackStack()
            }
        )
    }
}
