package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.RepeatDay
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.StepItem
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeContract
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.GrayE7
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.PinkFF
import com.devndev.homen.ui.theme.YellowFF
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.barhroom_icon
import homen.composeapp.generated.resources.checkbox_icon
import homen.composeapp.generated.resources.chore_count_format
import homen.composeapp.generated.resources.chore_difficulty_easy
import homen.composeapp.generated.resources.chore_difficulty_hard
import homen.composeapp.generated.resources.chore_difficulty_medium
import homen.composeapp.generated.resources.chore_info_difficulty
import homen.composeapp.generated.resources.chore_info_point_days
import homen.composeapp.generated.resources.cleaning_icon
import homen.composeapp.generated.resources.day_fri
import homen.composeapp.generated.resources.day_mon
import homen.composeapp.generated.resources.day_sat
import homen.composeapp.generated.resources.day_sun
import homen.composeapp.generated.resources.day_thu
import homen.composeapp.generated.resources.day_tue
import homen.composeapp.generated.resources.day_wed
import homen.composeapp.generated.resources.difficulty_high
import homen.composeapp.generated.resources.difficulty_low
import homen.composeapp.generated.resources.difficulty_lower_medium
import homen.composeapp.generated.resources.difficulty_medium
import homen.composeapp.generated.resources.difficulty_upper_medium
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
import homen.composeapp.generated.resources.kitchen_icon
import homen.composeapp.generated.resources.laundry_icon
import homen.composeapp.generated.resources.pin_black_icon
import homen.composeapp.generated.resources.pin_icon
import homen.composeapp.generated.resources.star_icon
import homen.composeapp.generated.resources.ticket_icon
import homen.composeapp.generated.resources.trash_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreatePreviewScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CreateHomeViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CreateHomeContract.Effect.NavToNext -> onNextClick()
                CreateHomeContract.Effect.PopBackStack -> onBackClick()
                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(true))
    }

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

        else -> ""
    }

    val packIcon = when (uiState.selectedPack) {
        StarterPackType.ROOMMATE -> Res.drawable.ticket_icon
        StarterPackType.DORMITORY -> Res.drawable.star_icon
        StarterPackType.MINIMAL -> Res.drawable.pin_icon
        else -> Res.drawable.ticket_icon
    }

    val packColor = when (uiState.selectedPack) {
        StarterPackType.ROOMMATE -> Blue4
        StarterPackType.DORMITORY -> YellowFF
        StarterPackType.MINIMAL -> PinkFF
        else -> Blue4
    }

    // Composable context에서 문자열 리소스 선언
    val easyLabel = stringResource(Res.string.chore_difficulty_easy)
    val mediumLabel = stringResource(Res.string.chore_difficulty_medium)
    val hardLabel = stringResource(Res.string.chore_difficulty_hard)
    val countFormat = stringResource(Res.string.chore_count_format)
    val applyBtnFormat = stringResource(Res.string.home_create_preview_apply_btn)
    val includedTitle = stringResource(Res.string.home_create_preview_included_chores)

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
                                                    viewModel.setEvent(
                                                        CreateHomeContract.Event.OnChoreChecked(
                                                            chore
                                                        )
                                                    )
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
                    onClick = { viewModel.setEvent(CreateHomeContract.Event.OnNextClick) },
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
                            viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(false))
                        }
                    }
            )
        }
    }
}

@Composable
fun ChorePreviewItem(
    chore: Chore,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val icon = when (chore.category) {
        ChoreCategory.TRASH.id -> Res.drawable.trash_icon
        ChoreCategory.CLEANING.id -> Res.drawable.cleaning_icon
        ChoreCategory.BATHROOM.id -> Res.drawable.barhroom_icon
        ChoreCategory.KITCHEN.id -> Res.drawable.kitchen_icon
        ChoreCategory.LAUNDRY.id -> Res.drawable.laundry_icon
        else -> Res.drawable.trash_icon
    }

    val mon = stringResource(Res.string.day_mon)
    val tue = stringResource(Res.string.day_tue)
    val wed = stringResource(Res.string.day_wed)
    val thu = stringResource(Res.string.day_thu)
    val fri = stringResource(Res.string.day_fri)
    val sat = stringResource(Res.string.day_sat)
    val sun = stringResource(Res.string.day_sun)

    val dLow = stringResource(Res.string.difficulty_low)
    val dLowerMedium = stringResource(Res.string.difficulty_lower_medium)
    val dMedium = stringResource(Res.string.difficulty_medium)
    val dUpperMedium = stringResource(Res.string.difficulty_upper_medium)
    val dHigh = stringResource(Res.string.difficulty_high)

    val infoFormat = stringResource(Res.string.chore_info_point_days)
    val diffFormat = stringResource(Res.string.chore_info_difficulty)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onToggle() },
        verticalAlignment = Alignment.Top
    ) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(ButtonGray, RoundedCornerShape(28.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    val daysText = chore.repeatDays.joinToString(",") { dayValue ->
                        when (dayValue) {
                            RepeatDay.MONDAY.value -> mon
                            RepeatDay.TUESDAY.value -> tue
                            RepeatDay.WEDNESDAY.value -> wed
                            RepeatDay.THURSDAY.value -> thu
                            RepeatDay.FRIDAY.value -> fri
                            RepeatDay.SATURDAY.value -> sat
                            RepeatDay.SUNDAY.value -> sun
                            else -> ""
                        }
                    }
                    val diffText = when (chore.difficulty) {
                        ChoreDifficulty.LOW -> dLow; ChoreDifficulty.LOWER_MEDIUM -> dLowerMedium
                        ChoreDifficulty.MEDIUM -> dMedium; ChoreDifficulty.UPPER_MEDIUM -> dUpperMedium
                        ChoreDifficulty.HIGH -> dHigh
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = HomeNTheme.typography.suitBold.toSpanStyle()) {
                                append(
                                    infoFormat.replace("n", chore.difficulty.point.toString())
                                        .replace("s", daysText)
                                )
                            }
                            append(diffFormat.replace("s", diffText))
                        },
                        fontSize = 10.sp,
                        color = Color.Black,
                        style = HomeNTheme.typography.suitRegular
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = chore.name,
                style = HomeNTheme.typography.suitBold,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Icon(
            painter = painterResource(Res.drawable.checkbox_icon),
            contentDescription = null,
            modifier = Modifier.size(17.dp),
            tint = if (isSelected) Color.Black else GrayE7
        )
    }
}
