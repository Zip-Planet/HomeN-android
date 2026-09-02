package com.devndev.homen.ui.main.assignment.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devndev.homen.core.common.util.Logger
import com.devndev.homen.ui.main.assignment.main.AssignmentScreen
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentTab
import com.devndev.homen.ui.main.home.main.navigation.HomeRoute
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.assignmentNav(
    navController: NavController,
    paddingValues: PaddingValues
) {
    composable<BottomNavItem.Assignment>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<BottomNavItem.Assignment>()
        val initialTab = if (route.isThisWeek) {
            AssignmentTab.THIS_WEEK
        } else {
            AssignmentTab.NEXT_WEEK
        }
        AssignmentScreen(
            initialTab = initialTab,
            onNavToChoreManage = { navController.navigate(HomeRoute.ChoreManage)},
            paddingValues = paddingValues
        )
    }
}
