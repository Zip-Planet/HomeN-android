package com.devndev.homen.ui.main.mypage.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.mypage.delegate.DelegateManagerScreen
import com.devndev.homen.ui.main.mypage.edit.ProfileSettingScreen
import com.devndev.homen.ui.main.mypage.main.MyPageScreen
import com.devndev.homen.ui.main.mypage.setting.HomeSettingScreen
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
            },
            onNavToHomeSetting = {
                navController.navigate(MyPageRoute.HomeSetting)
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

    composable<MyPageRoute.HomeSetting>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        HomeSettingScreen(
            onNavBack = {
                navController.popBackStack()
            },
            onNavToDelegateManager = {
                navController.navigate(MyPageRoute.DelegateManager)
            },
            onNavToHomeIntro = onNavToIntro
        )
    }

    composable<MyPageRoute.DelegateManager>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        DelegateManagerScreen(
            onNavBack = {
                navController.popBackStack()
            }
        )
    }
}
