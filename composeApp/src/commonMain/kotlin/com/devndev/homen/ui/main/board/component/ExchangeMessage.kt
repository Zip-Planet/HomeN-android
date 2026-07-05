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
import com.devndev.homen.ui.component.HomeN34Button
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.theme.BlueCAEAFC
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.Green28A049
import com.devndev.homen.ui.theme.HomeNTheme
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
    isMine: Boolean
) {
    val backgroundColor = if (isMine) BlueCAEAFC else Color.White
    val title = stringResource(Res.string.board_request_exchange_title).replace("s", "투다리김치우동")
    val highlightText = stringResource(Res.string.board_request_exchange_highlight_text)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)
        if (!isMine && startIndex != -1) {
            // "도움 요청" 앞부분 추가
            append(title.take(startIndex))

            // "도움 요청" 부분 색상 적용
            withStyle(style = SpanStyle(color = Green28A049)) {
                append(highlightText)
            }

            // "도움 요청" 뒷부분 추가
            append(title.substring(startIndex + highlightText.length))
        } else {
            append(title)
        }
    }
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
                text = "토요일 출장이라 대신해줄 사람~",
                style = HomeNTheme.typography.suitMedium,
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = "2026년 1월 5주차")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.farmer_avatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "투다리김치우동",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChoreExchangeCard(
            title = "욕실청소",
            day = "토",
            difficulty = "중상",
            points = "160P"
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
                painter = painterResource(Res.drawable.chef_avatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "왕만두",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isMine) {
            ChoreExchangeCard(
                title = "욕실청소",
                day = "토",
                difficulty = "중상",
                points = "160P"
            )
        } else {
            ChoreExchangeCard(
                title = "욕실청소",
                day = "토",
                difficulty = "중상",
                points = "160P"
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    HomeN34Button(
                        modifier = Modifier.weight(0.6f),
                        text = stringResource(Res.string.board_request_exchange_next_btn),
                        onClick = {},
                        color = ButtonGray
                    )

                    HomeN34Button(
                        modifier = Modifier.weight(0.4f),
                        text = stringResource(Res.string.board_request_exchange_accept_btn),
                        onClick = {},
                        color = Green28A049,
                        textColor = Color.White
                    )
                }
            }
        }

        if (isMine) {
            Spacer(modifier = Modifier.height(13.dp))
            HomeNButton(
                text = stringResource(Res.string.board_request_help_cancel_btn),
                onClick = {},
                color = Color.Black,
            )
        }
    }
}

@Composable
fun ExchangeAcceptMessage(
) {
    val title = stringResource(Res.string.board_request_exchange_accept_title).replace("s", "투다리김치우동")
    val highlightText = stringResource(Res.string.board_request_exchange_accept_highlight_text)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)

        append(title.take(startIndex))

        withStyle(style = SpanStyle(color = Green28A049)) {
            append(highlightText)
        }

        append(title.substring(startIndex + highlightText.length))
    }

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
                text = "토요일 출장이라 대신해줄 사람~",
                style = HomeNTheme.typography.suitMedium,
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        DateSection(date = "2026년 1월 5주차")

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.farmer_avatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "투다리김치우동",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChoreExchangeCard(
            title = "욕실청소",
            day = "토",
            difficulty = "중상",
            points = "160P"
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
                painter = painterResource(Res.drawable.chef_avatar),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "왕만두",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ChoreExchangeCard(
            title = "욕실청소",
            day = "토",
            difficulty = "중상",
            points = "160P"
        )
    }
}

@Composable
fun ExchangeEndMessage(
    isReject: Boolean
) {

    val title = if (isReject) {
        stringResource(Res.string.board_request_exchange_reject_title).replace("s", "투다리김치우동")
    } else {
        stringResource(Res.string.board_request_exchange_expire_title).replace("s", "투다리김치우동")
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

        append(title.take(startIndex))

        withStyle(style = SpanStyle(color = Green28A049)) {
            append(highlightText)
        }

        append(title.substring(startIndex + highlightText.length))
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
    ExchangeMessage(false)
}

@Preview
@Composable
fun ExchangeMessageMinePreview() {
    ExchangeMessage(true)
}

@Preview
@Composable
fun ExchangeAcceptMessagePreview() {
    ExchangeAcceptMessage()
}

@Preview
@Composable
fun ExchangeExpireMessagePreview() {
    ExchangeEndMessage(false)
}

@Preview
@Composable
fun ExchangeRejectMessagePreview() {
    ExchangeEndMessage(true)
}