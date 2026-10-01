package com.devndev.homen.ui.main.notification.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.notification.NotificationInboxScreen

fun NavGraphBuilder.notificationNav(
    navController: NavController,
    paddingValues: PaddingValues
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
            }
        )
    }
}
