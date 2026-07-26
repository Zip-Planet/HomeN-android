package com.devndev.homen.ui.main.reward.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.navigation.BottomNavItem
import com.devndev.homen.ui.main.reward.edit.RewardEditScreen
import com.devndev.homen.ui.main.reward.main.RewardScreen

fun NavGraphBuilder.rewardNav(navController: NavController) {
    composable<BottomNavItem.Reward>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        RewardScreen(
            onNavToEditReward = { rewardId, reward, point, isEdit ->
                navController.navigate(RewardRoute.EditReward(rewardId, reward, point, isEdit))
            }
        )
    }

    composable<RewardRoute.EditReward>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) { backStackEntry ->
        val route: RewardRoute.EditReward = backStackEntry.toRoute()
        RewardEditScreen(
            rewardId = route.rewardId,
            reward = route.reward,
            point = route.point,
            isEdit = route.isEdit,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
