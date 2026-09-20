package com.devndev.homen.ui.main.board.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.board.BoardChoreItem
import com.devndev.homen.core.domain.model.board.BoardMember
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.Dot
import com.devndev.homen.ui.component.HomeN34Button
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.theme.BlueCAEAFC
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.Green28A049
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.util.DateUtil
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board_request_exchange_accept_btn
import homen.composeapp.generated.resources.board_request_exchange_accept_highlight_text
import homen.composeapp.generated.resources.board_request_exchange_accept_title
import homen.composeapp.generated.resources.board_request_exchange_expire_highlight_text
import homen.composeapp.generated.resources.board_request_exchange_expire_message
import homen.composeapp.generated.resources.board_request_exchange_expire_title
import homen.composeapp.generated.resources.board_request_exchange_highlight_text
import homen.composeapp.generated.resources.board_request_exchange_next_btn
import homen.composeapp.generated.resources.board_request_exchange_reject_highlight_text
import homen.composeapp.generated.resources.board_request_exchange_reject_message
import homen.composeapp.generated.resources.board_request_exchange_reject_title
import homen.composeapp.generated.resources.board_request_exchange_title
import homen.composeapp.generated.resources.board_request_help_cancel_btn
import homen.composeapp.generated.resources.chat_icon
import homen.composeapp.generated.resources.chef_avatar
import homen.composeapp.generated.resources.exchange_icon
import homen.composeapp.generated.resources.farmer_avatar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExchangeMessage(
    isMine: Boolean,
    name: String,
    description: String,
    date: String,
    requesterItem: BoardChoreItem,
    targetName: String,
    targetItem: BoardChoreItem,
    onCancelClick: () -> Unit = {},
    onAcceptClick: () -> Unit = {},
    onRejectClick: () -> Unit = {}
) {
    val backgroundColor = if (isMine) BlueCAEAFC else Color.White
    val title = stringResource(Res.string.board_request_exchange_title).replace("s", name)
    val highlightText = stringResource(Res.string.board_request_exchange_highlight_text)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)
        if (!isMine && startIndex != -1) {
            append(title.take(startIndex))
            withStyle(style = SpanStyle(color = Green28A049)) {
                append(highlightText)
            }
            append(title.substring(startIndex + highlightText.length))
        } else {
            append(title)
        }
    }

    val requesterAvatar = AvatarType.fromId(requesterItem.assignee?.profileImage ?: 1).resource
    val targetAvatar = AvatarType.fromId(targetItem.assignee?.profileImage ?: 1).resource

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Text(
            text = annotatedTitle,
            style = HomeNTheme.typography.suitExtraBold,
            fontSize = 16.sp,
            color = Color.Black,
        )

        if (description.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.5.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = description,
                    style = HomeNTheme.typography.suitMedium,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = DateUtil.formatWeekOfMonth(date))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Image(
                painter = painterResource(requesterAvatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = name,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChoreExchangeCard(
            title = requesterItem.choreName,
            day = requesterItem.weekdayLabel,
            difficulty = ChoreDifficulty.fromId(requesterItem.difficulty).label,
            points = "${requesterItem.point}P"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(BottomGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.exchange_icon),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Image(
                painter = painterResource(targetAvatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = targetName,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isMine) {
            ChoreExchangeCard(
                title = targetItem.choreName,
                day = targetItem.weekdayLabel,
                difficulty = ChoreDifficulty.fromId(targetItem.difficulty).label,
                points = "${targetItem.point}P"
            )

            Spacer(modifier = Modifier.height(13.dp))
            HomeNButton(
                text = stringResource(Res.string.board_request_help_cancel_btn),
                onClick = onCancelClick,
                color = Color.Black,
            )
        } else {
            ChoreExchangeCard(
                title = targetItem.choreName,
                day = targetItem.weekdayLabel,
                difficulty = ChoreDifficulty.fromId(targetItem.difficulty).label,
                points = "${targetItem.point}P"
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    HomeN34Button(
                        modifier = Modifier.weight(0.6f),
                        text = stringResource(Res.string.board_request_exchange_next_btn),
                        onClick = onRejectClick,
                        color = ButtonGray
                    )

                    HomeN34Button(
                        modifier = Modifier.weight(0.4f),
                        text = stringResource(Res.string.board_request_exchange_accept_btn),
                        onClick = onAcceptClick,
                        color = Green28A049,
                        textColor = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ExchangeAcceptMessage(
    requester: BoardMember,
    acceptedBy: BoardMember,
    date: String,
    requesterItem: BoardChoreItem,
    targetItem: BoardChoreItem,
    description: String = ""
) {
    val title = stringResource(Res.string.board_request_exchange_accept_title).replace("s", acceptedBy.name)
    val highlightText = stringResource(Res.string.board_request_exchange_accept_highlight_text)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)

        if (startIndex != -1) {
            append(title.take(startIndex))
            withStyle(style = SpanStyle(color = Green28A049)) {
                append(highlightText)
            }
            append(title.substring(startIndex + highlightText.length))
        } else {
            append(title)
        }
    }

    val requesterAvatar = AvatarType.fromId(requester.profileImage ?: 1).resource
    val targetAvatar = AvatarType.fromId(acceptedBy.profileImage ?: 1).resource

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Text(
            text = annotatedTitle,
            style = HomeNTheme.typography.suitExtraBold,
            fontSize = 16.sp,
            color = Color.Black,
        )

        if (description.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.5.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = description,
                    style = HomeNTheme.typography.suitMedium,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = DateUtil.formatWeekOfMonth(date))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Image(
                painter = painterResource(requesterAvatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = requester.name,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChoreExchangeCard(
            title = targetItem.choreName,
            day = targetItem.weekdayLabel,
            difficulty = ChoreDifficulty.fromId(targetItem.difficulty).label,
            points = "${targetItem.point}P"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(BottomGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.exchange_icon),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Image(
                painter = painterResource(targetAvatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = acceptedBy.name,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChoreExchangeCard(
            title = requesterItem.choreName,
            day = requesterItem.weekdayLabel,
            difficulty = ChoreDifficulty.fromId(requesterItem.difficulty).label,
            points = "${requesterItem.point}P"
        )
    }
}

@Composable
fun ExchangeEndMessage(
    name: String,
    isReject: Boolean
) {

    val title = if (isReject) {
        stringResource(Res.string.board_request_exchange_reject_title).replace("s", name)
    } else {
        stringResource(Res.string.board_request_exchange_expire_title).replace("s", name)
    }
    val highlightText = if (isReject) {
        stringResource(Res.string.board_request_exchange_reject_highlight_text)
    } else {
        stringResource(Res.string.board_request_exchange_expire_highlight_text)
    }

    val message = if (isReject) {
        stringResource(Res.string.board_request_exchange_reject_message)
    } else {
        stringResource(Res.string.board_request_exchange_expire_message)
    }

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)

        if (startIndex != -1) {
            append(title.take(startIndex))
            withStyle(style = SpanStyle(color = Green28A049)) {
                append(highlightText)
            }
            append(title.substring(startIndex + highlightText.length))
        } else {
            append(title)
        }
    }
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = annotatedTitle,
            style = HomeNTheme.typography.suitExtraBold,
            fontSize = 16.sp,
            color = Color.Black,
        )

        Text(
            text = message,
            style = HomeNTheme.typography.suitMedium,
            fontSize = 12.sp,
            color = Color.Black,
        )
    }
}

@Preview
@Composable
fun ExchangeMessagePreview() {
    ExchangeMessage(
        isMine = false,
        name = "투다리김치우동",
        description = "토요일 출장이라 대신해줄 사람~",
        date = "2026-01-01",
        requesterItem = BoardChoreItem(
            id = 1,
            choreName = "욕실청소",
            weekday = 5,
            weekdayLabel = "토",
            difficulty = 4,
            point = 160,
            date = "2026-01-01",
            assignee = BoardMember(uid = "1", name = "투다리김치우동", profileImage = 6)
        ),
        targetName = "왕만두",
        targetItem = BoardChoreItem(
            id = 2,
            choreName = "주방 마감",
            weekday = 5,
            weekdayLabel = "토",
            difficulty = 3,
            point = 100,
            date = "2026-01-01",
            assignee = BoardMember(uid = "2", name = "왕만두", profileImage = 1)
        )
    )
}

@Preview
@Composable
fun ExchangeMessageMinePreview() {
    ExchangeMessage(
        isMine = true,
        name = "히히",
        description = "바꾸자!",
        date = "2026-01-01",
        requesterItem = BoardChoreItem(
            id = 1,
            choreName = "욕실 배수구 머리카락 치우기",
            weekday = 2,
            weekdayLabel = "수",
            difficulty = 3,
            point = 120,
            date = "2026-09-16",
            assignee = BoardMember(uid = "1", name = "히히", profileImage = 1)
        ),
        targetName = "하이",
        targetItem = BoardChoreItem(
            id = 2,
            choreName = "새러운집안일",
            weekday = 0,
            weekdayLabel = "월",
            difficulty = 1,
            point = 40,
            date = "2026-09-14",
            assignee = BoardMember(uid = "2", name = "하이", profileImage = 2)
        )
    )
}

@Preview
@Composable
fun ExchangeAcceptMessagePreview() {
    ExchangeAcceptMessage(
        requester = BoardMember(uid = "1", name = "투다리김치우동", profileImage = 6),
        acceptedBy = BoardMember(uid = "2", name = "왕만두", profileImage = 1),
        date = "2026-01-01",
        requesterItem = BoardChoreItem(
            id = 1,
            choreName = "욕실청소",
            weekday = 5,
            weekdayLabel = "토",
            difficulty = 4,
            point = 160,
            date = "2026-01-01",
            assignee = null
        ),
        targetItem = BoardChoreItem(
            id = 2,
            choreName = "주방 마감",
            weekday = 5,
            weekdayLabel = "토",
            difficulty = 3,
            point = 100,
            date = "2026-01-01",
            assignee = null
        )
    )
}

@Preview
@Composable
fun ExchangeExpireMessagePreview() {
    ExchangeEndMessage(name = "투다리김치우동", isReject = false)
}

@Preview
@Composable
fun ExchangeRejectMessagePreview() {
    ExchangeEndMessage(name = "투다리김치우동", isReject = true)
}
