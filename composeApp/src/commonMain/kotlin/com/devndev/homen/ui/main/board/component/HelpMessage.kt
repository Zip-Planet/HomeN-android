package com.devndev.homen.ui.main.board.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.devndev.homen.ui.component.Dot
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.BlueCAEAFC
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.OrangeFF8431
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.arrow_icon
import homen.composeapp.generated.resources.board_request_help_accept_highlight_text
import homen.composeapp.generated.resources.board_request_help_accept_title
import homen.composeapp.generated.resources.board_request_help_btn
import homen.composeapp.generated.resources.board_request_help_cancel_btn
import homen.composeapp.generated.resources.board_request_help_expire_highlight_text
import homen.composeapp.generated.resources.board_request_help_expire_message
import homen.composeapp.generated.resources.board_request_help_expire_title
import homen.composeapp.generated.resources.board_request_help_highlight_text
import homen.composeapp.generated.resources.board_request_help_title
import homen.composeapp.generated.resources.chat_icon
import homen.composeapp.generated.resources.chef_avatar
import homen.composeapp.generated.resources.farmer_avatar
import homen.composeapp.generated.resources.pin_black_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HelpMessage(
    isMine: Boolean
) {
    val backgroundColor = if (isMine) BlueCAEAFC else Color.White
    val title = stringResource(Res.string.board_request_help_title).replace("s", "투다리김치우동")
    val highlightText = stringResource(Res.string.board_request_help_highlight_text)
    val buttonColor = if (isMine) Color.Black else OrangeFF8431
    val buttonText =
        if (isMine) stringResource(Res.string.board_request_help_cancel_btn) else stringResource(Res.string.board_request_help_btn)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)
        if (!isMine && startIndex != -1) {
            // "도움 요청" 앞부분 추가
            append(title.take(startIndex))

            // "도움 요청" 부분 색상 적용
            withStyle(style = SpanStyle(color = OrangeFF8431)) {
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

        ChoreExchangeCard(
            title = "욕실청소",
            day = "토",
            difficulty = "중상",
            points = "160P"
        )

        Spacer(modifier = Modifier.height(13.dp))

        HomeNButton(
            text = buttonText,
            onClick = {},
            color = buttonColor,
        )
    }
}

@Composable
fun HelpAcceptMessage() {
    val title = stringResource(Res.string.board_request_help_accept_title).replace("s", "왕만두")
    val highlightText = stringResource(Res.string.board_request_help_accept_highlight_text)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)

        append(title.take(startIndex))

        withStyle(style = SpanStyle(color = OrangeFF8431)) {
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

            Icon(
                painter = painterResource(Res.drawable.arrow_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 1.dp)
                    .height(10.dp)
            )

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

        ChoreExchangeCard(
            title = "욕실청소",
            day = "토",
            difficulty = "중상",
            points = "160P"
        )
    }
}

@Composable
fun HelpExpireMessage() {
    val title = stringResource(Res.string.board_request_help_expire_title).replace("s", "투다리김치우동")
    val highlightText = stringResource(Res.string.board_request_help_expire_highlight_text)

    val annotatedTitle = buildAnnotatedString {
        val startIndex = title.indexOf(highlightText)

        append(title.take(startIndex))

        withStyle(style = SpanStyle(color = OrangeFF8431)) {
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
            text = stringResource(Res.string.board_request_help_expire_message),
            style = HomeNTheme.typography.suitMedium,
            fontSize = 12.sp,
            color = Color.Black,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChoreExchangeCard(
    title: String,
    day: String,
    difficulty: String,
    points: String,
    bottomContent: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BackgroundGray)
            .padding(10.dp),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(Res.drawable.pin_black_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(16.dp),
                tint = Color.Black
            )

            // FlowRow를 사용하여 텍스트가 길어지면 자동으로 점과 함께 줄바꿈되도록 함
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Center,
                itemVerticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = HomeNTheme.typography.suitSemiBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Dot(
                    width = 8,
                    dotSize = 2
                )

                Text(
                    text = day,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Dot(
                    width = 8,
                    dotSize = 2
                )

                Text(
                    text = "난이도 $difficulty",
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Dot(
                    width = 8,
                    dotSize = 2
                )

                Text(
                    text = points,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
        bottomContent()
    }
}

@Preview
@Composable
fun HelpMessagePreview() {
    HelpMessage(isMine = false)
}

@Preview
@Composable
fun HelpMessageMinePreview() {
    HelpMessage(isMine = true)
}

@Preview
@Composable
fun HelpAcceptMessagePreview() {
    HelpAcceptMessage()
}

@Preview
@Composable
fun HelpExpireMessagePreview() {
    HelpExpireMessage()
}


