package com.devndev.homen.ui.main.home.starterpackpreview

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
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
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.StepItem
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.home.starterpackpreview.viewmodel.StarterPackPreviewContract
import com.devndev.homen.ui.main.home.starterpackpreview.viewmodel.StarterPackPreviewViewModel
import com.devndev.homen.ui.main.homeintro.create.ChorePreviewItem
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.PinkFF
import com.devndev.homen.ui.theme.YellowFF
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chore_count_format
import homen.composeapp.generated.resources.chore_difficulty_easy
import homen.composeapp.generated.resources.chore_difficulty_hard
import homen.composeapp.generated.resources.chore_difficulty_medium
import homen.composeapp.generated.resources.home_create_pack1_msg
import homen.composeapp.generated.resources.home_create_pack1_title
import homen.composeapp.generated.resources.home_create_pack2_msg
import homen.composeapp.generated.resources.home_create_pack2_title
import homen.composeapp.generated.resources.home_create_pack3_msg
import homen.composeapp.generated.resources.home_create_pack3_title
import homen.composeapp.generated.resources.home_create_pack_badge
import homen.composeapp.generated.resources.home_create_pack_tooltip_msg
import homen.composeapp.generated.resources.home_create_pack_tooltip_title
import homen.composeapp.generated.resources.home_create_preview_apply_btn
import homen.composeapp.generated.resources.home_create_preview_included_chores
import homen.composeapp.generated.resources.home_starter_pack_tooltip_msg
import homen.composeapp.generated.resources.home_starter_pack_tooltip_title
import homen.composeapp.generated.resources.pin_black_icon
import homen.composeapp.generated.resources.pin_icon
import homen.composeapp.generated.resources.star_icon
import homen.composeapp.generated.resources.starter_pack_preview_title
import homen.composeapp.generated.resources.ticket_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StarterPackPreviewScreen(
    viewModel: StarterPackPreviewViewModel = koinViewModel(),
    packType: Int,
    onNavToBack: () -> Unit,
    onCreateChore: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(Unit) {
        viewModel.setEvent(StarterPackPreviewContract.Event.OnInit(packType))
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect->
            when (effect) {
                StarterPackPreviewContract.Effect.NavToBack -> {
                    onNavToBack()
                }
                StarterPackPreviewContract.Effect.NavToCreateChore -> {
                    onCreateChore()
                }
            }
        }
    }

    StarterPackPreviewContent(
        uiState = uiState,
        onBackClick = {
            viewModel.setEvent(StarterPackPreviewContract.Event.OnBackClick)
        },
        onChoreChecked = {
            viewModel.setEvent(StarterPackPreviewContract.Event.OnChoreChecked(it))
        },
        onApplyClick = {
            viewModel.setEvent(StarterPackPreviewContract.Event.OnApplyClick)
        },
        onTooltipToggle = {
            viewModel.setEvent(StarterPackPreviewContract.Event.OnTooltipToggle(it))
        }
    )

}

@Composable
fun StarterPackPreviewContent(
    uiState: StarterPackPreviewContract.State,
    onBackClick: () -> Unit,
    onChoreChecked: (Chore) -> Unit,
    onApplyClick: () -> Unit,
    onTooltipToggle: (Boolean) -> Unit,
) {
    val packTitle = when (uiState.selectedPack) {
        StarterPackType.ROOMMATE -> stringResource(Res.string.home_create_pack1_title)
        StarterPackType.DORMITORY -> stringResource(Res.string.home_create_pack2_title)
        StarterPackType.MINIMAL -> stringResource(Res.string.home_create_pack3_title)
        else -> ""
    }

    val packDesc = when (uiState.selectedPack) {
        StarterPackType.ROOMMATE -> stringResource(Res.string.home_create_pack1_msg).replace(
            "\n",
            " "
        )

        StarterPackType.DORMITORY -> stringResource(Res.string.home_create_pack2_msg).replace(
            "\n",
            " "
        )

        StarterPackType.MINIMAL -> stringResource(Res.string.home_create_pack3_msg).replace(
            "\n",
            " "
        )
    }

    val packIcon = when (uiState.selectedPack) {
        StarterPackType.ROOMMATE -> Res.drawable.ticket_icon
        StarterPackType.DORMITORY -> Res.drawable.star_icon
        StarterPackType.MINIMAL -> Res.drawable.pin_icon
    }

    val packColor = when (uiState.selectedPack) {
        StarterPackType.ROOMMATE -> Blue4
        StarterPackType.DORMITORY -> YellowFF
        StarterPackType.MINIMAL -> PinkFF
    }

    // Composable context에서 문자열 리소스 선언
    val easyLabel = stringResource(Res.string.chore_difficulty_easy)
    val mediumLabel = stringResource(Res.string.chore_difficulty_medium)
    val hardLabel = stringResource(Res.string.chore_difficulty_hard)
    val countFormat = stringResource(Res.string.chore_count_format)
    val applyBtnFormat = stringResource(Res.string.home_create_preview_apply_btn)
    val includedTitle = stringResource(Res.string.home_create_preview_included_chores)
    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.starter_pack_preview_title),
                onBackClick = {
                    onBackClick()
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = HomeNTheme.dimensions.topPadding,
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(packIcon),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.Unspecified
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        StepItem(
                            text = stringResource(Res.string.home_create_pack_badge).replace(
                                "n",
                                uiState.chores.size.toString()
                            ),
                            backgroundColor = packColor,
                            textColor = Color.Black,
                            width = 63.dp,
                            height = 17.dp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = packTitle,
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = packDesc,
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 12.sp,
                            color = Color.Black,
                            lineHeight = 1.6.em
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(Color.White)
                        .padding(
                            start = HomeNTheme.dimensions.horizontalPadding,
                            end = HomeNTheme.dimensions.horizontalPadding,
                            top = 30.dp
                        )
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(Res.drawable.pin_black_icon),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = includedTitle,
                                style = HomeNTheme.typography.suitExtraBold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                        ) {
                            val groupedChores = uiState.chores.groupBy { chore ->
                                when (chore.difficulty) {
                                    ChoreDifficulty.LOW, ChoreDifficulty.LOWER_MEDIUM -> "EASY"
                                    ChoreDifficulty.MEDIUM -> "MEDIUM"
                                    else -> "HARD"
                                }
                            }

                            groupedChores.forEach { (difficultyKey, chores) ->
                                item {
                                    val label = when (difficultyKey) {
                                        "EASY" -> easyLabel
                                        "MEDIUM" -> mediumLabel
                                        else -> hardLabel
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            text = "• " + countFormat
                                                .replace("s", label)
                                                .replace("n", chores.size.toString()),
                                            style = HomeNTheme.typography.suitBold,
                                            fontSize = 14.sp,
                                            color = Color.Black,
                                            modifier = Modifier.width(70.dp)
                                        )

                                        Column(
                                            modifier = Modifier.weight(1f),
                                            verticalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            chores.forEach { chore ->
                                                ChorePreviewItem(
                                                    chore = chore,
                                                    isSelected = uiState.selectedChores.contains(chore),
                                                    onToggle = {
                                                        onChoreChecked(chore)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            item { Spacer(modifier = Modifier.height(100.dp)) }
                        }
                    }

                    HomeNButton(
                        text = applyBtnFormat.replace("s", packTitle),
                        onClick = {
                            onApplyClick()
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = HomeNTheme.dimensions.bottomPadding)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 1.dp, end = HomeNTheme.dimensions.horizontalPadding),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TooltipButton {
                    onTooltipToggle(!uiState.showTooltip)
                }

                if (uiState.showTooltip) {
                    HomeNTooltip(
                        title = stringResource(Res.string.home_starter_pack_tooltip_title),
                        messages = listOf(stringResource(Res.string.home_starter_pack_tooltip_msg)),
                        onCloseClick = {
                            onTooltipToggle(false)
                        },
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures { }
                        }
                    )
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
}

@Preview
@Composable
fun StarterPackPreviewContentPreview() {
    StarterPackPreviewContent(
        uiState = StarterPackPreviewContract.State(),
        onBackClick = {},
        onChoreChecked = {},
        onApplyClick = {},
        onTooltipToggle = {}
    )
}