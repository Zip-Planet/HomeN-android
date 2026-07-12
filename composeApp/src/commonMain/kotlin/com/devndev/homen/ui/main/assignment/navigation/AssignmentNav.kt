package com.devndev.homen.ui.main.assignment.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devndev.homen.ui.main.assignment.main.AssignmentScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.assignmentNav(
    paddingValues: PaddingValues
) {
    composable<BottomNavItem.Assignment>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        AssignmentScreen(paddingValues = paddingValues)
    }
}
