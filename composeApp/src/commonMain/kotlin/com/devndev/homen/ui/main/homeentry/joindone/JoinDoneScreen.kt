package com.devndev.homen.ui.main.homeentry.joindone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.OsType
import com.devndev.homen.getPlatform
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.main.homeentry.joindone.viewmodel.JoinDoneContract
import com.devndev.homen.ui.main.homeentry.joindone.viewmodel.JoinDoneViewModel
import com.devndev.homen.ui.theme.DarkGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.enter_homen_btn
import homen.composeapp.generated.resources.home1_big_icon
import homen.composeapp.generated.resources.join_done_msg1
import homen.composeapp.generated.resources.join_done_msg2
import homen.composeapp.generated.resources.join_done_msg3
import homen.composeapp.generated.resources.join_done_subtitle
import homen.composeapp.generated.resources.join_done_title
import homen.composeapp.generated.resources.location_check_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JoinDoneScreen(
    onNavToHome: () -> Unit,
    viewModel: JoinDoneViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is JoinDoneContract.Effect.NavigateToHome -> onNavToHome()
            }
        }
    }

    HomeNScreen(
        topBar = {},
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val topSpace = if (getPlatform() == OsType.IOS) {
                120.dp
            } else {
                146.dp
            }
            Spacer(modifier = Modifier.height(topSpace))

            Text(
                text = stringResource(Res.string.join_done_title).replace("s", "골든빌 401"),
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 20.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(Res.string.join_done_subtitle),
                style = HomeNTheme.typography.suitMedium,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(80.dp))

            Icon(
                painter = painterResource(Res.drawable.home1_big_icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .width(236.dp)
                    .height(166.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                JoinDoneMsg(Res.drawable.chart_icon, stringResource(Res.string.join_done_msg1))
                JoinDoneMsg(
                    Res.drawable.location_check_icon,
                    stringResource(Res.string.join_done_msg2)
                )
                JoinDoneMsg(Res.drawable.clipboard_icon, stringResource(Res.string.join_done_msg3))
            }

            Spacer(modifier = Modifier.weight(1f))

            HomeNButton(
                text = stringResource(Res.string.enter_homen_btn),
                onClick = { viewModel.setEvent(JoinDoneContract.Event.OnConfirmClick) },
                enabled = true
            )
        }
    }
}

@Composable
fun JoinDoneMsg(
    res: DrawableResource,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(res),
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = DarkGray
        )

        Text(
            text = text,
            style = HomeNTheme.typography.suitMedium,
            fontSize = 12.sp,
            color = DarkGray
        )
    }
}