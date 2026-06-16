package com.devndev.homen.ui.main.home.starterpack

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.home.starterpack.viewmodel.StarterPackContract
import com.devndev.homen.ui.main.home.starterpack.viewmodel.StarterPackViewModel
import com.devndev.homen.ui.main.homeintro.create.PackItem
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.PinkFF
import com.devndev.homen.ui.theme.YellowFF
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home_create_pack1_msg
import homen.composeapp.generated.resources.home_create_pack1_title
import homen.composeapp.generated.resources.home_create_pack2_msg
import homen.composeapp.generated.resources.home_create_pack2_title
import homen.composeapp.generated.resources.home_create_pack3_msg
import homen.composeapp.generated.resources.home_create_pack3_title
import homen.composeapp.generated.resources.home_create_pack_msg
import homen.composeapp.generated.resources.home_create_pack_preview_btn
import homen.composeapp.generated.resources.home_create_pack_title
import homen.composeapp.generated.resources.home_create_pack_tooltip_msg
import homen.composeapp.generated.resources.home_create_pack_tooltip_title
import homen.composeapp.generated.resources.pin_icon
import homen.composeapp.generated.resources.star_icon
import homen.composeapp.generated.resources.starter_pack_select_title
import homen.composeapp.generated.resources.ticket_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StarterPackScreen(
    viewModel: StarterPackViewModel = koinViewModel(),
    onNavToPreview: (Int) -> Unit,
    onNavToBack: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect->
            when (effect) {
                is StarterPackContract.Effect.NavToPreview -> {
                    onNavToPreview(effect.staterPackType)
                }

                StarterPackContract.Effect.NavToBack -> {
                    onNavToBack()
                }
            }
        }
    }

    StarterPackContent(
        uiState = uiState,
        onPackSelected = {
            viewModel.setEvent(StarterPackContract.Event.OnPackSelected(it))
        },
        onPreviewClick = {
            viewModel.setEvent(StarterPackContract.Event.OnPreviewClick)
        },
        onTooltipToggle = {
            viewModel.setEvent(StarterPackContract.Event.OnTooltipToggle(it))
        },
        onBackClick = {
            viewModel.setEvent(StarterPackContract.Event.OnBackClick)
        }
    )
}

@Composable
fun StarterPackContent(
    uiState: StarterPackContract.State,
    onPackSelected: (StarterPackType) -> Unit,
    onPreviewClick: () -> Unit,
    onTooltipToggle: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.starter_pack_select_title),
                onBackClick = onBackClick
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    top = 42.dp,
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
                            onPackSelected(StarterPackType.ROOMMATE)
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
                            onPackSelected(StarterPackType.DORMITORY)
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
                            onPackSelected(StarterPackType.MINIMAL)
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                HomeNButton(
                    text = stringResource(Res.string.home_create_pack_preview_btn),
                    onClick = {
                        onPreviewClick()
                    },
                    enabled = uiState.selectedPack != null
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 1.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TooltipButton {
                    onTooltipToggle(!uiState.showTooltip)
                }

                if (uiState.showTooltip) {
                    HomeNTooltip(
                        title = stringResource(Res.string.home_create_pack_tooltip_title),
                        messages = listOf(stringResource(Res.string.home_create_pack_tooltip_msg)),
                        onCloseClick = {
                            onTooltipToggle(false)
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
                            onTooltipToggle(false)
                        }
                    }
            )
        }
    }
}

@Preview
@Composable
fun StarterPackContentPreview() {
    StarterPackContent(
        uiState = StarterPackContract.State(),
        onPackSelected = {},
        onPreviewClick = {},
        onTooltipToggle = {},
        onBackClick = {}
    )
}