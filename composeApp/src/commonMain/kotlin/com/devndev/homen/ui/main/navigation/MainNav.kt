package com.devndev.homen.ui.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.component.MainBottomBar
import com.devndev.homen.ui.main.assignment.navigation.assignmentNav
import com.devndev.homen.ui.main.board.navigation.boardNav
import com.devndev.homen.ui.main.home.main.navigation.HomeRoute
import com.devndev.homen.ui.main.home.main.navigation.homeNav
import com.devndev.homen.ui.main.homeintro.navigation.HomeIntroRoute
import com.devndev.homen.ui.main.homeintro.navigation.homeIntroNav
import com.devndev.homen.ui.main.mypage.navigation.myPageNav
import com.devndev.homen.ui.main.present.navigation.presentNav
import com.devndev.homen.ui.main.viewmodel.MainContract
import com.devndev.homen.ui.main.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNav(
    onNavToIntro: () -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(Unit) {
        viewModel.setEvent(MainContract.Event.OnMainNav)
    }

    val uiState by viewModel.viewState

    val hasHome = uiState.hasHome ?: return
    val startDestination: Any = if (hasHome) BottomNavItem.Home else HomeIntroRoute.Selection

    val isChoreManage = currentDestination?.hasRoute<HomeRoute.ChoreManage>() == true ||
            currentDestination?.hasRoute<HomeRoute.CreateChore>() == true
    val isHomeIntroRoute = currentDestination?.hasRoute<HomeIntroRoute.Selection>() == true ||
            currentDestination?.hasRoute<HomeIntroRoute.JoinGraph>() == true ||
            currentDestination?.hasRoute<HomeIntroRoute.CreateGraph>() == true

    Scaffold(
        bottomBar = {
            if (hasHome && !isHomeIntroRoute && !isChoreManage) {
                MainBottomBar(navController = mainNavController)
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        NavHost(
            navController = mainNavController,
            startDestination = startDestination,
            modifier = Modifier.padding(PaddingValues(0.dp))
//            modifier = Modifier.padding(if (hasHome && !isHomeIntroRoute) paddingValues else PaddingValues(0.dp))
        ) {
            homeIntroNav(
                navController = mainNavController,
                onNavToMain = {
                    mainNavController.navigate(BottomNavItem.Home) {
                        popUpTo<HomeIntroRoute.Selection> { inclusive = true }
                    }
                },
                onNavToIntro = onNavToIntro
            )

            homeNav(mainNavController)
            boardNav()
            assignmentNav()
            presentNav()
            myPageNav()
        }
    }
}
