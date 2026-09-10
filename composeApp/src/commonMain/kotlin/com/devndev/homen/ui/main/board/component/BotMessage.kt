package com.devndev.homen.ui.main.board.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.board.BoardPayload
import com.devndev.homen.core.domain.model.board.BotType
import com.devndev.homen.ui.component.Dot
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.util.DateUtil
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board_assignment_card_total_chore
import homen.composeapp.generated.resources.board_assignment_confirmed_card_title
import homen.composeapp.generated.resources.board_assignment_created_card_title
import homen.composeapp.generated.resources.board_division_plan_message_btn
import homen.composeapp.generated.resources.board_report_message_btn
import homen.composeapp.generated.resources.board_report_message_title
import homen.composeapp.generated.resources.board_report_mvp_message
import homen.composeapp.generated.resources.board_reward_message_btn
import homen.composeapp.generated.resources.board_reward_message_title
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.farmer_avatar
import homen.composeapp.generated.resources.pin_black_icon
import homen.composeapp.generated.resources.present_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DivisionPlanMessage(
    date: String,
    botType: String,
    assignment: BoardPayload.Assignment,
    onClick: () -> Unit
) {
    val text = when (botType) {
        BotType.ASSIGNMENT_CREATED.type -> stringResource(Res.string.board_assignment_created_card_title)
        else -> stringResource(Res.string.board_assignment_confirmed_card_title)
    }
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.chart_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = text,
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = DateUtil.formatWeekOfMonth(date))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.pin_black_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )

            Text(
                text = stringResource(Res.string.board_assignment_card_total_chore).replace("s", assignment.totalCount.toString()),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        HomeNButton(
            text = stringResource(Res.string.board_division_plan_message_btn),
            onClick = onClick
        )
    }
}


@Composable
fun ReportMessage() {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.clipboard_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(Res.string.board_report_message_title),
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = "2026년 1월 5주차")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Res.drawable.farmer_avatar),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = stringResource(Res.string.board_report_mvp_message),
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = "투다리 김치우동",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )

            Dot(
                width = 8,
                height = 2,
                dotSize = 2
            )

            Text(
                text = "560P",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(Res.drawable.chart_icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "8/8 완료",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )

            Dot(
                width = 8,
                height = 2,
                dotSize = 2
            )

            Text(
                text = "100%",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        HomeNButton(
            text = stringResource(Res.string.board_report_message_btn),
            onClick = {}
        )
    }
}

@Composable
fun RewardMessage(
    date: String,
    reward: BoardPayload.Reward?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .widthIn(min = 160.dp)
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.present_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = stringResource(Res.string.board_reward_message_title),
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = DateUtil.formatWeekOfMonth(date))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(Res.drawable.farmer_avatar),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = reward?.claimedBy ?: "",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )

            Dot(
                width = 8,
                height = 2,
                dotSize = 2
            )

            // TODO::SHKIM 서버 response 추가 시 포인트 수정
            Text(
                text = "${reward?.goalPoint.toString()}P",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(Res.drawable.present_icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = reward?.rewardName ?: "",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )

            Dot(
                width = 8,
                height = 2,
                dotSize = 2
            )

            Text(
                text = "${reward?.goalPoint.toString()}P",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        HomeNButton(
            text = stringResource(Res.string.board_reward_message_btn),
            onClick = onClick
        )
    }
}

@Composable
fun DateSection(date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Dot(
            width = 14,
            height = 2,
            dotSize = 2
        )

        Text(
            text = date,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 10.sp,
            color = Color.Black
        )
    }
}
