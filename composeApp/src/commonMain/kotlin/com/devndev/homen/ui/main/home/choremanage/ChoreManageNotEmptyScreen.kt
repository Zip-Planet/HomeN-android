package com.devndev.homen.ui.main.home.choremanage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.RepeatDay
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageContract
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chore_info_difficulty
import homen.composeapp.generated.resources.chore_info_point_days
import homen.composeapp.generated.resources.chore_manage_tooltip_msg
import homen.composeapp.generated.resources.chore_manage_tooltip_title
import homen.composeapp.generated.resources.chore_not_empty_description
import homen.composeapp.generated.resources.chore_not_empty_title
import homen.composeapp.generated.resources.edit_alt_icon
import homen.composeapp.generated.resources.floating_btn_icon
import homen.composeapp.generated.resources.menu_dot_icon
import homen.composeapp.generated.resources.trash_alt_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChoreManageNotEmptyScreen(
    uiState: ChoreManageContract.State,
    onTooltipClick: (Boolean) -> Unit,
    onAddButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = stringResource(Res.string.chore_not_empty_title).replace(
                        "s",
                        uiState.homeName
                    ),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(Res.string.chore_not_empty_description),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(Color.White)
                    .padding(
                        start = HomeNTheme.dimensions.horizontalPadding,
                        end = HomeNTheme.dimensions.horizontalPadding,
                        top = 6.dp
                    ),
            ) {
                uiState.chores.forEach {
                    item {
                        ChoreItem(it)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 1.dp, end = 17.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TooltipButton {
                onTooltipClick(!uiState.isNotEmptyChoreTooltipShow)
            }

            // TODO 문구 수정 요청 필요
            if (uiState.isNotEmptyChoreTooltipShow) {
                HomeNTooltip(
                    title = stringResource(Res.string.chore_manage_tooltip_title),
                    messages = listOf(stringResource(Res.string.chore_manage_tooltip_msg)),
                    onCloseClick = {
                        onTooltipClick(false)

                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { /* 툴팁 내부 클릭 시 닫히지 않도록 보호 */ }
                    }
                )
            }
        }

        Icon(
            painter = painterResource(Res.drawable.floating_btn_icon),
            contentDescription = "add chore icon",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 36.dp, bottom = 55.dp)
                .size(51.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onAddButtonClick()
                }
        )
    }
}

@Composable
fun ChoreItem(chore: Chore) {
    var isExpanded by remember { mutableStateOf(false) }
    val choreResource = ChoreCategory.fromId(chore.category).resource
    val infoFormat = stringResource(Res.string.chore_info_point_days)
    val diffFormat = stringResource(Res.string.chore_info_difficulty)
    val daysText = chore.repeatDays.joinToString(",") { dayValue ->
        when (dayValue) {
            RepeatDay.MONDAY.value -> RepeatDay.MONDAY.day
            RepeatDay.TUESDAY.value -> RepeatDay.TUESDAY.day
            RepeatDay.WEDNESDAY.value -> RepeatDay.WEDNESDAY.day
            RepeatDay.THURSDAY.value -> RepeatDay.THURSDAY.day
            RepeatDay.FRIDAY.value -> RepeatDay.FRIDAY.day
            RepeatDay.SATURDAY.value -> RepeatDay.SATURDAY.day
            RepeatDay.SUNDAY.value -> RepeatDay.SUNDAY.day
            else -> ""
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 14.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(choreResource),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.height(36.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(17.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Blue4)
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = HomeNTheme.typography.suitBold.toSpanStyle()) {
                            append(
                                infoFormat.replace("n", chore.difficulty.point.toString())
                                    .replace("s", daysText)
                            )
                        }
                        append(diffFormat.replace("s", chore.difficulty.label))
                    },
                    fontSize = 10.sp,
                    color = Color.Black,
                    style = HomeNTheme.typography.suitRegular
                )
            }
            Text(
                text = chore.name,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInHorizontally(
                    initialOffsetX = { it / 2 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { it / 2 },
                    animationSpec = tween(150)
                ) + fadeOut()

            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_alt_icon),
                        contentDescription = "edit chore icon",
                        modifier = Modifier.size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                // 편집 로직
                            },
                    )
                    Icon(
                        painter = painterResource(Res.drawable.trash_alt_icon),
                        contentDescription = "delete chore icon",
                        modifier = Modifier.size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                // 삭제 로직
                            },
                    )
                }
            }
            Icon(
                painter = painterResource(Res.drawable.menu_dot_icon),
                contentDescription = "chore menu icon",
                modifier = Modifier.size(20.dp)
                    .background(color = Color.White)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        isExpanded = !isExpanded
                    },
            )
        }
    }
}

@Preview
@Composable
fun ChoreItemPreview() {
    ChoreItem(
        Chore(
            id = 1,
            category = 1,
            name = "청소",
            description = "열심히 해라",
            repeatDays = listOf(1, 3, 4),
            difficulty = ChoreDifficulty.LOWER_MEDIUM
        )
    )
}