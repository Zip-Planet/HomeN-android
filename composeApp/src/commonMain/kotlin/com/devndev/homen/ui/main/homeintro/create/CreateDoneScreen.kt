package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.devndev.homen.core.domain.model.home.HomeIconType
import com.devndev.homen.getPlatform
import com.devndev.homen.ui.common.bigResource
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.InvitePopup
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeContract
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.DarkGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.enter_homen_btn
import homen.composeapp.generated.resources.home_create_done_title
import homen.composeapp.generated.resources.home_create_invite_btn
import homen.composeapp.generated.resources.join_done_msg1
import homen.composeapp.generated.resources.join_done_msg2
import homen.composeapp.generated.resources.join_done_msg3
import homen.composeapp.generated.resources.join_done_subtitle
import homen.composeapp.generated.resources.location_check_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateDoneScreen(
    viewModel: CreateHomeViewModel = koinViewModel(),
    onNavToMain: () -> Unit
) {
    BackHandler {

    }

    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect->
            when (effect) {
                CreateHomeContract.Effect.NavToNext -> {
                    onNavToMain()
                }
                else -> {

                }
            }
        }
    }

    if (uiState.isShowInvitePopup) {
        InvitePopup(
            homeName = uiState.homeName,
            inviteCode = "ABCABC",
            onClose = {
                viewModel.setEvent(CreateHomeContract.Event.OnInviteClick(false))
            },
            onCopy = {

            },
            onKakaoShare = { },
            onGeneralShare = {}
        )
    }

    CreateDoneContent(
        homeName = uiState.homeName,
        homeIcon = (uiState.avatarId ?: 0) + 1,
        onConfirmClick = { viewModel.setEvent(CreateHomeContract.Event.OnNextClick) },
        onInviteClick = { viewModel.setEvent(CreateHomeContract.Event.OnInviteClick(true)) }
    )
}

@Composable
private fun CreateDoneContent(
    homeName: String,
    homeIcon: Int,
    onConfirmClick: () -> Unit,
    onInviteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                start = HomeNTheme.dimensions.horizontalPadding,
                end = HomeNTheme.dimensions.horizontalPadding,
                bottom = HomeNTheme.dimensions.bottomPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val topSpace = if (getPlatform() == OsType.IOS) 130.dp else 183.dp
        Spacer(modifier = Modifier.height(topSpace))
        val homeResource = HomeIconType.fromId(homeIcon).bigResource

        Icon(
            painter = painterResource(homeResource),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .width(236.dp)
                .height(166.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(Res.string.home_create_done_title).replace("s", homeName),
            style = HomeNTheme.typography.suitExtraBold,
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(Res.string.join_done_subtitle),
            style = HomeNTheme.typography.suitMedium,
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(37.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BackgroundGray, shape = RoundedCornerShape(10.dp))
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            CreateDoneMsg(Res.drawable.chart_icon, stringResource(Res.string.join_done_msg1))
            CreateDoneMsg(
                Res.drawable.location_check_icon,
                stringResource(Res.string.join_done_msg2)
            )
            CreateDoneMsg(Res.drawable.clipboard_icon, stringResource(Res.string.join_done_msg3))
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            HomeNButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.home_create_invite_btn),
                onClick = onInviteClick,
                enabled = true
            )
            HomeNButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.enter_homen_btn),
                onClick = onConfirmClick,
                enabled = true
            )
        }
    }
}

@Composable
fun CreateDoneMsg(
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

@Preview
@Composable
fun JoinDoneScreenPreView() {
    HomeNTheme {
        CreateDoneContent(
            homeName = "누리빌",
            homeIcon = 1,
            onConfirmClick = {},
            onInviteClick = {}
        )
    }
}