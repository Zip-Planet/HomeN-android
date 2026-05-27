package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.HomeNStepIndicator
import com.devndev.homen.ui.component.NavTransitions
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.main.homeintro.navigation.HomeIntroRoute
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home_create_step1_title
import homen.composeapp.generated.resources.home_create_step2_title
import homen.composeapp.generated.resources.home_create_step3_title
import homen.composeapp.generated.resources.home_create_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateHomeFlowScreen(
    onNavToMain: () -> Unit,
    onExitFlow: () -> Unit,
    viewModel: CreateHomeViewModel = koinViewModel()
) {
    val innerNavController = rememberNavController()
    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentStep = when {
        currentDestination?.hasRoute<HomeIntroRoute.CreateProfile>() == true -> 1
        currentDestination?.hasRoute<HomeIntroRoute.CreatePack>() == true -> 2
        currentDestination?.hasRoute<HomeIntroRoute.CreateReward>() == true -> 3
        else -> 1
    }

    val isIndicatorVisible = currentDestination?.hasRoute<HomeIntroRoute.PackPreview>() != true

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_create_title),
                onBackClick = {
                    if (!innerNavController.popBackStack()) {
                        onExitFlow()
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            if (isIndicatorVisible) {
                Spacer(modifier = Modifier.height(21.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                        .fillMaxWidth()
                        .height(41.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ButtonGray)
                        .padding(horizontal = 13.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    HomeNStepIndicator(
                        currentStep = currentStep,
                        stepTitles = listOf(
                            stringResource(Res.string.home_create_step1_title),
                            stringResource(Res.string.home_create_step2_title),
                            stringResource(Res.string.home_create_step3_title),
                        ),
                    )
                }
            }

            NavHost(
                navController = innerNavController,
                startDestination = HomeIntroRoute.CreateProfile,
                enterTransition = NavTransitions.enterTransition,
                exitTransition = NavTransitions.exitTransition,
                popEnterTransition = NavTransitions.popEnterTransition,
                popExitTransition = NavTransitions.popExitTransition,
                modifier = Modifier.weight(1f)
            ) {
                composable<HomeIntroRoute.CreateProfile> {
                    CreateProfileScreen(
                        viewModel = viewModel,
                        onNextClick = { innerNavController.navigate(HomeIntroRoute.CreatePack) },
                        onBackClick = {
                            if (!innerNavController.popBackStack()) onExitFlow()
                        }
                    )
                }
                composable<HomeIntroRoute.CreatePack> {
                    CreatePackScreen(
                        viewModel = viewModel,
                        onBackClick = { innerNavController.popBackStack() },
                        onCreateChoreClick = { innerNavController.navigate(HomeIntroRoute.CreateReward) },
                        onPreviewClick = { innerNavController.navigate(HomeIntroRoute.PackPreview) }
                    )
                }
                composable<HomeIntroRoute.PackPreview> {
                    CreatePreviewScreen(
                        viewModel = viewModel,
                        onNextClick = { innerNavController.navigate(HomeIntroRoute.CreateReward) },
                        onBackClick = { innerNavController.popBackStack() }
                    )
                }
                composable<HomeIntroRoute.CreateReward> {
                    CreateRewardScreen(
                        viewModel = viewModel,
                        onBackClick = { innerNavController.popBackStack() },
                        onCompleteClick = { onNavToMain() },
                    )
                }
            }
        }
    }
}
