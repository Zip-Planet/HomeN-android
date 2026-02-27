package com.devndev.homen.ui.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.component.MainBottomBar
import com.devndev.homen.ui.main.assignment.navigation.assignmentNav
import com.devndev.homen.ui.main.board.navigation.boardNav
import com.devndev.homen.ui.main.home.navigation.homeNav
import com.devndev.homen.ui.main.homeentry.navigation.HomeEntryRoute
import com.devndev.homen.ui.main.homeentry.navigation.homeEntryNav
import com.devndev.homen.ui.main.mypage.navigation.myPageNav
import com.devndev.homen.ui.main.present.navigation.presentNav
import com.devndev.homen.ui.main.viewmodel.MainContract
import com.devndev.homen.ui.main.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNav(
    viewModel: MainViewModel = koinViewModel()
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val uiState by viewModel.viewState

    val startDestination = if (uiState.hasHome) BottomNavItem.Home.route else HomeEntryRoute.Selection.route

    val isHomeEntryRoute = currentRoute?.startsWith("entry_") == true

    Scaffold(
        bottomBar = {
            if (uiState.hasHome && !isHomeEntryRoute) {
                MainBottomBar(navController = mainNavController)
            }
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        NavHost(
            navController = mainNavController,
            startDestination = startDestination,
            modifier = Modifier.padding(if (uiState.hasHome && !isHomeEntryRoute) paddingValues else PaddingValues(0.dp))
        ) {
            homeEntryNav(
                navController = mainNavController,
                onNavToMain = {
                    viewModel.setEvent(MainContract.Event.OnHomeEntryComplete(hasHome = true))
                    mainNavController.navigate(BottomNavItem.Home.route) {
                        popUpTo(HomeEntryRoute.Selection.route) { inclusive = true }
                    }
                }
            )

            homeNav()
            boardNav()
            assignmentNav()
            presentNav()
            myPageNav()
        }
    }
}
