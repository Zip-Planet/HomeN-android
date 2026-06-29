package com.devndev.homen.ui.main.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.board.BoardType
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board_bot_type
import homen.composeapp.generated.resources.board_request_exchange_type
import homen.composeapp.generated.resources.board_request_help_type
import homen.composeapp.generated.resources.bot_icon
import homen.composeapp.generated.resources.fire_heart_icon
import homen.composeapp.generated.resources.flashlight_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoardHeader(boardType: BoardType) {
    val icon = when (boardType) {
        BoardType.BOT -> Res.drawable.bot_icon
        BoardType.REQUEST_HELP -> Res.drawable.fire_heart_icon
        BoardType.REQUEST_EXCHANGE -> Res.drawable.flashlight_icon
    }

    val text = when (boardType) {
        BoardType.BOT -> Res.string.board_bot_type
        BoardType.REQUEST_HELP -> Res.string.board_request_help_type
        BoardType.REQUEST_EXCHANGE -> Res.string.board_request_exchange_type
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
        }
        Text(
            text = stringResource(text),
            style = HomeNTheme.typography.suitSemiBold,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}


@Preview
@Composable
fun BoardTypePreview() {
    Column(modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        BoardType.entries.forEach { type ->
            BoardHeader(boardType = type)
        }
    }
}