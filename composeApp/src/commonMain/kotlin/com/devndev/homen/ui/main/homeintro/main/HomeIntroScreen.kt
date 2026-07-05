package com.devndev.homen.ui.main.homeintro.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.homeintro.main.viewmodel.HomeIntroContract
import com.devndev.homen.ui.main.homeintro.main.viewmodel.HomeIntroViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.create_btn_msg
import homen.composeapp.generated.resources.home_entry_guide_msg
import homen.composeapp.generated.resources.home_entry_guide_title
import homen.composeapp.generated.resources.home_icon
import homen.composeapp.generated.resources.home_start_title
import homen.composeapp.generated.resources.join_btn_msg
import homen.composeapp.generated.resources.key_icon
import homen.composeapp.generated.resources.logout
import homen.composeapp.generated.resources.next_button
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeIntroScreen(
    onNavToCreation: () -> Unit,
    onNavToCodeEnter: () -> Unit,
    onNavToIntro: () -> Unit,
    viewModel: HomeIntroViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState
    var selectedOption by remember { mutableStateOf(0) }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeIntroContract.Effect.NavigateToCreateHome -> onNavToCreation()
                is HomeIntroContract.Effect.NavigateToJoinHome -> onNavToCodeEnter()
                HomeIntroContract.Effect.NavigateToSplash -> onNavToIntro()
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_start_title),
                isBackVisible = false
            )
        },
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 24.dp,
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding
                ),
        ) {
            Text(
                text = stringResource(Res.string.home_entry_guide_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(Res.string.home_entry_guide_msg),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            EntryOptionCard(
                title = stringResource(Res.string.create_btn_msg),
                icon = Res.drawable.home_icon,
                isSelected = selectedOption == 1,
                onClick = {
                    selectedOption = 1
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            EntryOptionCard(
                title = stringResource(Res.string.join_btn_msg),
                icon = Res.drawable.key_icon,
                isSelected = selectedOption == 2,
                onClick = {
                    selectedOption = 2
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        viewModel.setEvent(HomeIntroContract.Event.OnLogoutClick)
                    },
                    text = stringResource(Res.string.logout),
                    style = HomeNTheme.typography.suitLight.copy(textDecoration = TextDecoration.Underline),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                if (selectedOption == 0) {
                    Spacer(modifier = Modifier.height(48.dp))
                } else {
                    HomeNButton(
                        text = stringResource(Res.string.next_button),
                        onClick = {
                            if (selectedOption == 1) {
                                viewModel.setEvent(HomeIntroContract.Event.OnCreateHomeClick)
                            } else if (selectedOption == 2) {
                                viewModel.setEvent(HomeIntroContract.Event.OnJoinHomeClick)
                            }
                        },
                        enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun EntryOptionCard(
    title: String,
    icon: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(top = 15.dp, bottom = 15.dp, start = 19.dp, end = 19.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = HomeNTheme.typography.suitExtraBold,
            fontSize = 20.sp,
            color = Color.Black
        )

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}
