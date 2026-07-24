package com.devndev.homen.ui.main.assignment.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.home.MemberPoint
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentContract
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentStatus
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentTab
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.assignment_confirm_btn
import homen.composeapp.generated.resources.assignment_next_week_confirmed_msg
import homen.composeapp.generated.resources.assignment_next_week_suggested_msg
import homen.composeapp.generated.resources.assignment_next_week_title
import homen.composeapp.generated.resources.assignment_pic_msg
import homen.composeapp.generated.resources.assignment_point_title
import homen.composeapp.generated.resources.assignment_this_week_confirmed_msg
import homen.composeapp.generated.resources.assignment_this_week_suggested_manager_msg
import homen.composeapp.generated.resources.assignment_this_week_suggested_msg
import homen.composeapp.generated.resources.assignment_this_week_title
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.chore_info_difficulty
import homen.composeapp.generated.resources.chore_info_point_days
import homen.composeapp.generated.resources.diamond_icon
import homen.composeapp.generated.resources.division_plan
import homen.composeapp.generated.resources.home_my_info_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AssignmentContent(
    uiState: AssignmentContract.State,
    onMemberClick: (String) -> Unit = {},
    onWeekSelected: (Int) -> Unit = {},
    onConfirmClick: () -> Unit
) {
    val title = if (uiState.selectedTab == AssignmentTab.THIS_WEEK) {
        stringResource(Res.string.assignment_this_week_title)
    } else {
        stringResource(Res.string.assignment_next_week_title)
    }

    val message = if (uiState.assignment?.status == AssignmentStatus.CONFIRMED.status) {
        if (uiState.selectedTab == AssignmentTab.THIS_WEEK) {
            stringResource(Res.string.assignment_this_week_confirmed_msg)
        } else {
            stringResource(Res.string.assignment_next_week_confirmed_msg)
        }

    } else {
        if (uiState.isManager) {
            stringResource(Res.string.assignment_this_week_suggested_manager_msg)
        } else {
            if (uiState.selectedTab == AssignmentTab.THIS_WEEK) {
                stringResource(Res.string.assignment_this_week_suggested_msg)
            } else {
                stringResource(Res.string.assignment_next_week_suggested_msg)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (uiState.selectedTab != AssignmentTab.HISTORY) {
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                    text = title,
                    style = HomeNTheme.typography.suitBold,
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                    text = message,
                    style = HomeNTheme.typography.suitRegular,
                    color = Color.Black,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(Color.White)
                    .padding(
                        start = HomeNTheme.dimensions.horizontalPadding,
                        end = HomeNTheme.dimensions.horizontalPadding,
                        top = 30.dp,
                        bottom = if (uiState.isConfirmButtonExist) 80.dp else 40.dp
                    )

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.diamond_icon),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Black
                    )

                    Text(
                        text = stringResource(Res.string.assignment_point_title),
                        style = HomeNTheme.typography.suitExtraBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                uiState.memberPoints.forEach { memberPoint ->
                    PointItem(memberPoint)

                    Spacer(modifier = Modifier.height(20.dp))
                }
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.chart_icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )

                    Text(
                        text = stringResource(Res.string.division_plan),
                        style = HomeNTheme.typography.suitExtraBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (uiState.selectedTab == AssignmentTab.HISTORY) {
                        HistoryWeekSelector(
                            weekOffset = uiState.weekOffset,
                            onWeekSelected = {
                                onWeekSelected(it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssignmentMemberChip(
                        member = "전체",
                        isSelected = uiState.selectedMember == "전체",
                        onMemberClick = { onMemberClick("전체") },
                        index = 0,
                        isTotal = true
                    )

                    uiState.memberPoints.forEachIndexed { index, member ->
                        AssignmentMemberChip(
                            member = member.name,
                            isSelected = uiState.selectedMember == member.name,
                            onMemberClick = { onMemberClick(it) },
                            index = index,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))

                uiState.selectedAssignments.forEach { assignment ->
                    AssignmentItem(assignment)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        if (uiState.isConfirmButtonExist) {
            HomeNButton(
                text = stringResource(Res.string.assignment_confirm_btn),
                onClick = {
                    onConfirmClick()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 17.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun PointItem(
    memberPoint: MemberPoint
) {
    val profileResource = AvatarType.fromId(memberPoint.profileImage!!).resource
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color = BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(profileResource),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = memberPoint.name,
            style = HomeNTheme.typography.suitBold,
            fontSize = 13.sp,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "+ ${memberPoint.expectedPoint}P",
            style = HomeNTheme.typography.suitBold,
            fontSize = 13.sp,
            color = Color.Black
        )

    }
}
@Composable
fun AssignmentMemberChip(
    member: String,
    isSelected: Boolean,
    onMemberClick: (String) -> Unit,
    index: Int,
    isTotal: Boolean = false
) {
    val nameText = if(index == 0) {
        if (isTotal) {
            member
        } else {
            stringResource(Res.string.home_my_info_name)
        }
    } else {
        member
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(13.dp))
            .background(if (isSelected) Color.Black else Color.White)
            .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(13.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onMemberClick(member)
            }
            .padding(horizontal = 10.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center) {
        Text(
            text = nameText,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 10.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun AssignmentItem(
    assignment: AssignmentItem
) {
    val choreResource = ChoreCategory.fromId(assignment.category).resource
    val infoFormat = stringResource(Res.string.chore_info_point_days)
    val diffFormat = stringResource(Res.string.chore_info_difficulty)
    Row(
        modifier = Modifier.fillMaxWidth()
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
            modifier = Modifier.height(51.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(17.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(ButtonGray)
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = HomeNTheme.typography.suitBold.toSpanStyle()) {
                            append(
                                infoFormat.replace("n", assignment.point.toString())
                                    .replace("s", assignment.weekdayLabel)
                            )
                        }
                        append(diffFormat.replace("s", assignment.difficultyLabel))
                    },
                    fontSize = 10.sp,
                    color = Color.Black,
                    style = HomeNTheme.typography.suitRegular
                )
            }
            Text(
                text = assignment.choreName,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black
            )
            Text(
                text = stringResource(Res.string.assignment_pic_msg).replace("s", assignment.assignee?.name ?: ""),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = Color.Black
            )
        }
    }
}



@Preview
@Composable
fun AssignmentContentPreview() {
    AssignmentContent(
        uiState = AssignmentContract.State(),
        onMemberClick = {},
        onConfirmClick = {}
    )
}
