package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.StepItem
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeContract
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.alarm_icon
import homen.composeapp.generated.resources.create_home_icon
import homen.composeapp.generated.resources.create_pack_icon
import homen.composeapp.generated.resources.create_reward_icon
import homen.composeapp.generated.resources.home_create_onboarding_btn
import homen.composeapp.generated.resources.home_create_onboarding_msg
import homen.composeapp.generated.resources.home_create_onboarding_step1_msg
import homen.composeapp.generated.resources.home_create_onboarding_step1_title
import homen.composeapp.generated.resources.home_create_onboarding_step2_msg
import homen.composeapp.generated.resources.home_create_onboarding_step2_title
import homen.composeapp.generated.resources.home_create_onboarding_step3_msg
import homen.composeapp.generated.resources.home_create_onboarding_step3_title
import homen.composeapp.generated.resources.home_create_onboarding_time_msg
import homen.composeapp.generated.resources.home_create_title
import homen.composeapp.generated.resources.light_bulb
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateOnboardingScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CreateHomeViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CreateHomeContract.Effect.NavToNext -> onNextClick()
                is CreateHomeContract.Effect.PopBackStack -> onBackClick()
                else -> {}
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_create_title),
                onBackClick = { viewModel.setEvent(CreateHomeContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding,
                    top = HomeNTheme.dimensions.topPadding
                )
        ) {
            Text(
                text = stringResource(Res.string.home_create_onboarding_msg),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                lineHeight = 1.6.em,
                letterSpacing = (-0.022).em,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(42.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                OnboardingStepGuideItem(
                    step = 1,
                    title = stringResource(Res.string.home_create_onboarding_step1_title),
                    description = stringResource(Res.string.home_create_onboarding_step1_msg),
                    icon = Res.drawable.create_home_icon,
                    showLine = true
                )
                OnboardingStepGuideItem(
                    step = 2,
                    title = stringResource(Res.string.home_create_onboarding_step2_title),
                    description = stringResource(Res.string.home_create_onboarding_step2_msg),
                    icon = Res.drawable.create_pack_icon,
                    showLine = true
                )
                OnboardingStepGuideItem(
                    step = 3,
                    title = stringResource(Res.string.home_create_onboarding_step3_title),
                    description = stringResource(Res.string.home_create_onboarding_step3_msg),
                    icon = Res.drawable.create_reward_icon,
                    showLine = false
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 17.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.alarm_icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(Res.string.home_create_onboarding_time_msg),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            HomeNButton(
                text = stringResource(Res.string.home_create_onboarding_btn),
                onClick = {
                    viewModel.setEvent(CreateHomeContract.Event.OnNextClick)
                },
                enabled = true
            )
        }
    }
}

@Composable
fun OnboardingStepGuideItem(
    step: Int,
    title: String,
    description: String,
    icon: DrawableResource,
    showLine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(60.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = Color.Unspecified
                )
            }

            if (showLine) {
                VerticalDashedLine(
                    modifier = Modifier.height(48.dp).width(5.dp),
                )
            }
        }

        Column(
            modifier = Modifier.height(60.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            StepItem(
                text = "STEP $step",
                backgroundColor = Color.Black,
                textColor = Color.White
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = HomeNTheme.typography.suitBold,
                fontSize = 16.sp,
                color = Color.Black,
                lineHeight = 1.6.em,
                letterSpacing = (-0.022).em,

                )
            Text(
                text = description,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 12.sp,
                color = Color.Black,
                lineHeight = 1.4.em,
                letterSpacing = (-0.022).em,
            )
        }
    }
}

@Composable
fun VerticalDashedLine(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 25f), 0f)
        drawLine(
            color = color,
            start = Offset(size.width / 2, 0f),
            end = Offset(size.width / 2, size.height),
            pathEffect = pathEffect,
            strokeWidth = size.width,
            cap = StrokeCap.Round
        )
    }
}
