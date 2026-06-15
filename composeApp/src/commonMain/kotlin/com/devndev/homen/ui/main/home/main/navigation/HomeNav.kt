package com.devndev.homen.ui.main.home.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.home.choremanage.ChoreManageScreen
import com.devndev.homen.ui.main.home.createchore.CreateChoreScreen
import com.devndev.homen.ui.main.home.main.HomeScreen
import com.devndev.homen.ui.main.navigation.BottomNavItem

fun NavGraphBuilder.homeNav(navController: NavController) {
    composable<BottomNavItem.Home> {
        HomeScreen(
            onNavToChoreManage = {
                navController.navigate(HomeRoute.ChoreManage)
            }
        )
    }
    composable<HomeRoute.ChoreManage>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        ChoreManageScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onNavToCreateChore = {
                navController.navigate(HomeRoute.CreateChore)
            },
            onNavToEditChore = { chore ->
                navController.navigate(HomeRoute.EditChore(chore))
            }
        )
    }

    composable<HomeRoute.CreateChore>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        CreateChoreScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable<HomeRoute.EditChore>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) { backStackEntry ->
        val route: HomeRoute.EditChore = backStackEntry.toRoute()
        CreateChoreScreen(
            onBackClick = {
                navController.popBackStack()
            },
            isEdit = true,
            choreId = route.choreId
        )
    }
}
