package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.StepItem
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeContract
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.GrayE7
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.PinkFF
import com.devndev.homen.ui.theme.YellowFF
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.checkbox_icon
import homen.composeapp.generated.resources.home_create_pack1_msg
import homen.composeapp.generated.resources.home_create_pack1_title
import homen.composeapp.generated.resources.home_create_pack2_msg
import homen.composeapp.generated.resources.home_create_pack2_title
import homen.composeapp.generated.resources.home_create_pack3_msg
import homen.composeapp.generated.resources.home_create_pack3_title
import homen.composeapp.generated.resources.home_create_pack_add_manual_btn
import homen.composeapp.generated.resources.home_create_pack_badge
import homen.composeapp.generated.resources.home_create_pack_msg
import homen.composeapp.generated.resources.home_create_pack_preview_btn
import homen.composeapp.generated.resources.home_create_pack_title
import homen.composeapp.generated.resources.home_create_pack_tooltip_msg
import homen.composeapp.generated.resources.home_create_pack_tooltip_title
import homen.composeapp.generated.resources.pin_icon
import homen.composeapp.generated.resources.star_icon
import homen.composeapp.generated.resources.ticket_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreatePackScreen(
    onCreateChoreClick: () -> Unit,
    onPreviewClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CreateHomeViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CreateHomeContract.Effect.NavToNext -> {}
                CreateHomeContract.Effect.PopBackStack -> onBackClick()
                CreateHomeContract.Effect.NavToCreateChore -> onCreateChoreClick()
                CreateHomeContract.Effect.NavToPreview -> onPreviewClick()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = HomeNTheme.dimensions.horizontalPadding,
                end = HomeNTheme.dimensions.horizontalPadding,
                top = 52.dp,
                bottom = HomeNTheme.dimensions.bottomPadding,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.home_create_pack_title),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = stringResource(Res.string.home_create_pack_msg),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                lineHeight = 1.6.em,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(35.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PackItem(
                    title = stringResource(Res.string.home_create_pack1_title),
                    description = stringResource(Res.string.home_create_pack1_msg),
                    icon = Res.drawable.ticket_icon,
                    num = 6,
                    isSelected = uiState.selectedPack == StarterPackType.ROOMMATE,
                    badgeColor = Blue4,
                    onClick = {
                        viewModel.setEvent(
                            CreateHomeContract.Event.OnPackSelected(
                                StarterPackType.ROOMMATE
                            )
                        )
                    }
                )
                PackItem(
                    title = stringResource(Res.string.home_create_pack2_title),
                    description = stringResource(Res.string.home_create_pack2_msg),
                    icon = Res.drawable.star_icon,
                    num = 5,
                    isSelected = uiState.selectedPack == StarterPackType.DORMITORY,
                    badgeColor = YellowFF,
                    onClick = {
                        viewModel.setEvent(
                            CreateHomeContract.Event.OnPackSelected(
                                StarterPackType.DORMITORY
                            )
                        )
                    }
                )
                PackItem(
                    title = stringResource(Res.string.home_create_pack3_title),
                    description = stringResource(Res.string.home_create_pack3_msg),
                    icon = Res.drawable.pin_icon,
                    num = 4,
                    isSelected = uiState.selectedPack == StarterPackType.MINIMAL,
                    badgeColor = PinkFF,
                    onClick = {
                        viewModel.setEvent(
                            CreateHomeContract.Event.OnPackSelected(
                                StarterPackType.MINIMAL
                            )
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeNButton(
                    text = stringResource(Res.string.home_create_pack_add_manual_btn),
                    onClick = { /* TODO: 직접 추가 */ },
                    modifier = Modifier.weight(1f),
                )

                HomeNButton(
                    text = stringResource(Res.string.home_create_pack_preview_btn),
                    onClick = { viewModel.setEvent(CreateHomeContract.Event.OnPreviewClick) },
                    modifier = Modifier.weight(1f),
                    enabled = uiState.selectedPack != null
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 1.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TooltipButton {
                viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(!uiState.showTooltip))
            }

            if (uiState.showTooltip) {
                HomeNTooltip(
                    title = stringResource(Res.string.home_create_pack_tooltip_title),
                    messages = listOf(stringResource(Res.string.home_create_pack_tooltip_msg)),
                    onCloseClick = {
                        viewModel.setEvent(
                            CreateHomeContract.Event.OnTooltipToggle(
                                false
                            )
                        )
                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { /* 툴팁 내부 클릭 시 닫히지 않도록 보호 */ }
                    }
                )
            }
        }
    }
    if (uiState.showTooltip) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(false))
                    }
                }
        )
    }
}

@Composable
fun PackItem(
    title: String,
    description: String,
    num: Int,
    icon: DrawableResource,
    isSelected: Boolean,
    badgeColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BackgroundGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                StepItem(
                    text = stringResource(Res.string.home_create_pack_badge).replace(
                        "n",
                        num.toString()
                    ),
                    backgroundColor = badgeColor,
                    textColor = Color.Black,
                    width = 63.dp,
                    height = 17.dp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = title,
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = description,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black,
                    lineHeight = 1.6.em
                )
            }


            Icon(
                painter = painterResource(Res.drawable.checkbox_icon),
                modifier = Modifier
                    .size(17.dp),
                contentDescription = "package select checkbox",
                tint = if (isSelected) Color.Black else GrayE7
            )
        }
    }
}
