package com.devndev.homen.ui.main.home.main.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.main.home.choredetail.ChoreDetailScreen
import com.devndev.homen.ui.main.home.choremanage.ChoreManageScreen
import com.devndev.homen.ui.main.home.createchore.CreateChoreScreen
import com.devndev.homen.ui.main.home.main.HomeScreen
import com.devndev.homen.ui.main.home.memo.MemoScreen
import com.devndev.homen.ui.main.home.starterpack.StarterPackScreen
import com.devndev.homen.ui.main.home.starterpackpreview.StarterPackPreviewScreen
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
    ) { backStackEntry ->
        val deletedChoreId by backStackEntry.savedStateHandle
            .getStateFlow<Int?>("deleted_chore_id", null)
            .collectAsState()

        ChoreManageScreen(
            deletedChoreId = deletedChoreId,
            onDeleteConsumed = {
                backStackEntry.savedStateHandle["deleted_chore_id"] = null
            },
            onBackClick = {
                navController.popBackStack()
            },
            onNavToCreateChore = {
                navController.navigate(HomeRoute.CreateChore)
            },
            onNavToEditChore = { chore ->
                navController.navigate(HomeRoute.EditChore(chore))
            },
            onNavToChoreDetail = { chore ->
                navController.navigate(HomeRoute.ChoreDetail(chore))
            },
            onNavToStaterPack = {
                navController.navigate(HomeRoute.StarterPack)
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

    composable<HomeRoute.ChoreDetail>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) { backStackEntry ->
        val route: HomeRoute.ChoreDetail = backStackEntry.toRoute()
        ChoreDetailScreen(
            onBackClick = {
                navController.popBackStack()
            },
            choreId = route.choreId,
            onNavToMemo = { memoId, content, isEdit ->
                navController.navigate(HomeRoute.Memo(route.choreId, memoId, content, isEdit))
            },
            onDeleteChoreSuccess = { deletedId ->
                navController.previousBackStackEntry?.savedStateHandle?.set("deleted_chore_id", deletedId)
                navController.popBackStack()
            }
        )
    }

    composable<HomeRoute.Memo>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) { backStackEntry ->
        val route: HomeRoute.Memo = backStackEntry.toRoute()
        MemoScreen(
            choreId = route.choreId,
            memoId = route.memoId,
            content = route.content,
            isEdit = route.isEdit,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }

    composable<HomeRoute.StarterPack>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) {
        StarterPackScreen(
            onNavToPreview = {
                navController.navigate(HomeRoute.StarterPackPreview(it))
            },
            onNavToBack = {
                navController.popBackStack()
            }
        )
    }

    composable<HomeRoute.StarterPackPreview>(
        enterTransition = NavTransitions.enterTransition,
        exitTransition = NavTransitions.exitTransition,
        popEnterTransition = NavTransitions.popEnterTransition,
        popExitTransition = NavTransitions.popExitTransition
    ) { backStackEntry ->
        val route: HomeRoute.StarterPackPreview = backStackEntry.toRoute()
        StarterPackPreviewScreen(
            packType = route.starterPackType,
            onNavToBack = {
                navController.popBackStack()
            },
            onCreateChore = {
                navController.navigate(HomeRoute.ChoreManage) {
                    popUpTo(HomeRoute.ChoreManage) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            }
        )
    }
}
