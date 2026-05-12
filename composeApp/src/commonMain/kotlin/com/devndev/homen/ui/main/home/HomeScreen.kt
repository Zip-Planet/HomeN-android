package com.devndev.homen.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.HomeIconType
import com.devndev.homen.ui.common.smallResource
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.intro.register.viewmodel.RegisterContract
import com.devndev.homen.ui.intro.register.viewmodel.RegisterStep
import com.devndev.homen.ui.main.home.viewmodel.HomeContract
import com.devndev.homen.ui.main.home.viewmodel.HomeViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue2
import com.devndev.homen.ui.theme.Blue4736FC
import com.devndev.homen.ui.theme.Blue60ABFB
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.app_logo
import homen.composeapp.generated.resources.arrow_icon
import homen.composeapp.generated.resources.award_icon
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.chore
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.division_plan
import homen.composeapp.generated.resources.home_bottom_section_title
import homen.composeapp.generated.resources.home_chore_manage_msg
import homen.composeapp.generated.resources.home_create_division_plan_btn
import homen.composeapp.generated.resources.home_create_division_plan_msg
import homen.composeapp.generated.resources.home_division_plan_msg
import homen.composeapp.generated.resources.home_division_plan_status_msg1
import homen.composeapp.generated.resources.home_mvp_section_title
import homen.composeapp.generated.resources.home_progress_section_title
import homen.composeapp.generated.resources.home_report_status_msg2
import homen.composeapp.generated.resources.home_total_member_count
import homen.composeapp.generated.resources.next_button
import homen.composeapp.generated.resources.pin_black_icon
import homen.composeapp.generated.resources.report
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState
    val homeIcon = HomeIconType.fromId(uiState.homeIcon).smallResource

    HomeNScreen(
        topBar = {
            NotificationTopBar(
                title = stringResource(Res.string.app_logo),
                onNotificationClick = {}
            )
        },
        isLoading = uiState.isLoading
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenHeight = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().heightIn(min = screenHeight)
                ) {
                    Spacer(modifier = Modifier.height(27.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = HomeNTheme.dimensions.horizontalPadding,
                                end = HomeNTheme.dimensions.horizontalPadding
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(homeIcon),
                                contentDescription = null,
                                modifier = Modifier.size(33.dp),
                                tint = Color.Unspecified
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = uiState.homeName,
                            style = HomeNTheme.typography.suitExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stringResource(Res.string.home_total_member_count).replace(
                                "n",
                                uiState.totalMember.toString()
                            ),
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = painterResource(Res.drawable.arrow_icon),
                            contentDescription = "todo",
                            modifier = Modifier.height(13.dp).width(10.dp),
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    HomeProgressSection(uiState)
                    Spacer(modifier = Modifier.height(26.dp))
                    HomeDivisionSection(
                        modifier = Modifier.weight(1f),
                        uiState = uiState,
                        onMemberClick = { viewModel.setEvent(HomeContract.Event.OnMemberSelected(it)) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeProgressSection(
    uiState: HomeContract.State,
) {
    val progress =
        if (uiState.totalChore > 0) uiState.completedChore.toFloat() / uiState.totalChore else 0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = HomeNTheme.dimensions.horizontalPadding,
                end = HomeNTheme.dimensions.horizontalPadding
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 20.dp, horizontal = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.pin_black_icon),
                    contentDescription = null,
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(Res.string.home_progress_section_title),
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "50%",
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(14.dp)
                        .background(BackgroundGray, RoundedCornerShape(99.dp))
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .background(Blue4736FC, RoundedCornerShape(99.dp))
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${uiState.completedChore}/${uiState.totalChore}",
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 10.dp, horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.award_icon),
                contentDescription = null,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = stringResource(Res.string.home_mvp_section_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.mvpName,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .size(2.dp)
                        .background(color = Color.Black, shape = CircleShape)
                )

                Text(
                    text = "560P",
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HomeManageItem(
                modifier = Modifier.weight(1f),
                onClick = {},
                iconColor = Blue4736FC,
                iconSize = 16,
                titleText = stringResource(Res.string.division_plan),
                icon = Res.drawable.chart_icon
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.home_division_plan_msg),
                        style = HomeNTheme.typography.suitRegular,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .size(2.dp)
                            .background(color = Color.Black, shape = CircleShape)
                    )
                    Text(
                        text = stringResource(Res.string.home_division_plan_status_msg1),
                        style = HomeNTheme.typography.suitRegular,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(9.dp))
            HomeManageItem(
                modifier = Modifier.weight(1f),
                onClick = {},
                iconColor = Blue2,
                iconSize = 20,
                titleText = stringResource(Res.string.chore),
                icon = Res.drawable.pin_black_icon
            ) {
                Text(
                    text = stringResource(Res.string.home_chore_manage_msg),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(9.dp))
            HomeManageItem(
                modifier = Modifier.weight(1f),
                onClick = {},
                iconColor = Blue60ABFB,
                iconSize = 16,
                titleText = stringResource(Res.string.report),
                icon = Res.drawable.clipboard_icon
            ) {
                Text(
                    text = stringResource(Res.string.home_report_status_msg2) + "50%",
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun HomeDivisionSection(
    modifier: Modifier,
    uiState: HomeContract.State,
    onMemberClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding, vertical = 30.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.chart_icon),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = stringResource(Res.string.home_bottom_section_title),
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            uiState.members.forEach { name ->
                val isSelected = uiState.selectedMember == name
                MemberChip(
                    name = name,
                    isSelected = isSelected,
                    onMemberClick = { onMemberClick(it) }
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = stringResource(Res.string.home_create_division_plan_msg),
            style = HomeNTheme.typography.suitRegular,
            fontSize = 14.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(26.dp))
        HomeNButton(
            text = stringResource(Res.string.home_create_division_plan_btn),
            onClick = {  },
        )
    }
}

@Composable
fun MemberChip(
    name: String,
    isSelected: Boolean,
    onMemberClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(13.dp))
            .background(if (isSelected) Color.Black else Color.White)
            .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(13.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onMemberClick(name)
            }
            .padding(horizontal = 10.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center) {
        Text(
            text = name,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 10.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun HomeManageItem(
    modifier: Modifier,
    isExist: Boolean = true,
    onClick: () -> Unit = {},
    iconColor: Color,
    iconSize: Int,
    titleText: String,
    icon: DrawableResource,
    bottomContent: @Composable () -> Unit = {},
) {
    val containerColor = if (isExist) Color.White else ButtonGray
    val iconColor = if (isExist) iconColor else BottomGray
    val textColor = if (isExist) Color.Black else BottomGray
    Column(
        modifier = modifier
            .height(103.dp)
            .background(
                color = containerColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 14.dp, horizontal = 11.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(iconSize.dp),
            colorFilter = ColorFilter.tint(iconColor)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = titleText,
            style = HomeNTheme.typography.suitBold,
            fontSize = 14.sp,
            color = textColor
        )
        Spacer(modifier = Modifier.height(5.dp))
        bottomContent()
    }
}

@Preview
@Composable
fun HomeProgressSectionPreview() {
    HomeNTheme {
        HomeProgressSection(
            HomeContract.State(
                totalChore = 25,
                completedChore = 16,
                mvpName = "투다리김치우동"
            )
        )
    }
}

@Preview
@Composable
fun HomeBottomSectionPreview() {
    HomeNTheme {
        HomeDivisionSection(
            modifier = Modifier,
            uiState = HomeContract.State(
                members = listOf("나", "김치투다리우동", "김수환")
            ),
            onMemberClick = {}
        )
    }
}