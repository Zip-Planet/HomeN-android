package com.devndev.homen.ui.main.homeintro.joinconfirm

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.home.HomeIconType
import com.devndev.homen.core.domain.model.home.Member
import com.devndev.homen.core.domain.model.user.User
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.common.smallResource
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel.JoinConfirmContract
import com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel.JoinConfirmViewModel
import com.devndev.homen.ui.theme.Blue2
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chef_avatar
import homen.composeapp.generated.resources.confirm_home_member
import homen.composeapp.generated.resources.confirm_home_member_count
import homen.composeapp.generated.resources.confirm_home_title
import homen.composeapp.generated.resources.created_date
import homen.composeapp.generated.resources.guard_avatar
import homen.composeapp.generated.resources.hero_avatar
import homen.composeapp.generated.resources.home1_small_icon
import homen.composeapp.generated.resources.home_entry_title
import homen.composeapp.generated.resources.invite_code
import homen.composeapp.generated.resources.manager
import homen.composeapp.generated.resources.member
import homen.composeapp.generated.resources.user_plus
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JoinConfirmScreen(
    code: String,
    onNavToDone: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: JoinConfirmViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is JoinConfirmContract.Effect.NavigateToDone -> onNavToDone()
                is JoinConfirmContract.Effect.PopBackStack -> onBackClick()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(JoinConfirmContract.Event.OnInit(code))
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_entry_title),
                onBackClick = { viewModel.setEvent(JoinConfirmContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = HomeNTheme.dimensions.topPadding,
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(Res.string.confirm_home_title),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(35.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(13.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        val homeIcon = HomeIconType.fromId(uiState.imageId).smallResource
                        Icon(
                            painter = painterResource(homeIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(33.dp)
                                .align(Alignment.Center),
                            tint = Color.Unspecified
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.homeName,
                            style = HomeNTheme.typography.suitExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                        Text(
                            text = stringResource(Res.string.confirm_home_member_count).replace(
                                "n",
                                uiState.members.size.toString()
                            ),
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(13.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        9.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(28.dp))
                            .padding(vertical = 5.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.created_date),
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 12.sp,
                            color = Color.Black
                        )

                        Text(
                            text = "·",
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 12.sp,
                            color = Color.Black
                        )

                        Text(
                            text = uiState.createdAt,
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }

                    Row(
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(28.dp))
                            .padding(vertical = 5.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.invite_code),
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 12.sp,
                            color = Color.Black
                        )

                        Text(
                            text = "·",
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 12.sp,
                            color = Color.Black
                        )

                        Text(
                            text = code,
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(27.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(vertical = 20.dp, horizontal = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.user_plus),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                        )

                        Text(
                            text = stringResource(Res.string.confirm_home_member),
                            style = HomeNTheme.typography.suitExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }

                    uiState.members.sortedByDescending {it.role == 1 }.forEach {
                        UserSimpleInfo(it)
                    }
                }
            }
            HomeNButton(
                text = stringResource(Res.string.home_entry_title),
                onClick = { viewModel.setEvent(JoinConfirmContract.Event.OnJoinClick) },
                enabled = true,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun UserSimpleInfo(member: Member) {
    val icon = AvatarType.fromId(member.profileImage?: 1).resource
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp),
            tint = Color.Unspecified
        )

        Text(
            text = member.name,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 14.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        var text = stringResource(Res.string.manager)
        var backgroundColor = Blue2
        var textColor = Color.White

        if (member.role == 2) {
            text = stringResource(Res.string.member)
            backgroundColor = ButtonGray
            textColor = Color.Black
        }

        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(13.dp))
                .padding(vertical = 2.dp, horizontal = 5.dp)
        ) {
            Text(
                text = text,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = textColor
            )
        }
    }
}
