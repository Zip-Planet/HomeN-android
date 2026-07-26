package com.devndev.homen.ui.main.reward.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.navigation.BottomNavItem
import com.devndev.homen.ui.main.reward.main.RewardScreen

fun NavGraphBuilder.rewardNav() {
    composable<BottomNavItem.Reward>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        RewardScreen()
    }
}
