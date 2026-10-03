package com.devndev.homen.ui.main.notification.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.core.domain.model.notification.NotificationCategory
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.notification.NotificationInboxScreen

fun NavGraphBuilder.notificationNav(
    navController: NavController,
    paddingValues: PaddingValues,
    onNavToHome: () -> Unit = {},
    onNavToAssignment: () -> Unit = {},
    onNavToBoard: () -> Unit = {},
    onNavToReward: () -> Unit = {}
) {
    composable<NotificationRoute.NotificationInbox>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        NotificationInboxScreen(
            onNavBack = {
                navController.popBackStack()
            },
            onNavigateToCategory = { category ->
                when (category) {
                    NotificationCategory.ALL,
                    NotificationCategory.HOME_MEMBER -> onNavToHome()
                    NotificationCategory.ASSIGNMENT -> onNavToAssignment()
                    NotificationCategory.BOARD -> onNavToBoard()
                    NotificationCategory.REWARD -> onNavToReward()
                    NotificationCategory.REPORT -> {} // Report screen not ready
                }
            }
        )
    }
}
