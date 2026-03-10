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
import com.devndev.homen.core.domain.model.home.User
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel.JoinConfirmContract
import com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel.JoinConfirmViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue2
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chef_avatar
import homen.composeapp.generated.resources.confirm_home_member_count
import homen.composeapp.generated.resources.confirm_home_member_list_count
import homen.composeapp.generated.resources.confirm_home_title
import homen.composeapp.generated.resources.created_date
import homen.composeapp.generated.resources.guard_avatar
import homen.composeapp.generated.resources.hero_avatar
import homen.composeapp.generated.resources.home1_small_icon
import homen.composeapp.generated.resources.home_entry_title
import homen.composeapp.generated.resources.invite_code
import homen.composeapp.generated.resources.user_plus
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun JoinConfirmScreen(
    onNavToDone: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: JoinConfirmViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    val tempMembers = listOf(
        User("인기스탁", 1, false),
        User("투다리김치우동", 0, true),
        User("나는벌레", 2, false)
    )

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is JoinConfirmContract.Effect.NavigateToDone -> onNavToDone()
                is JoinConfirmContract.Effect.PopBackStack -> onBackClick()
            }
        }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = HomeNTheme.dimensions.topPadding,
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding
                )
        ) {
            Text(
                text = stringResource(Res.string.confirm_home_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    // TODO 집 아이콘 설정
                    Icon(
                        painter = painterResource(Res.drawable.home1_small_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                }

                Text(
                    text = "골든빌401",
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Text(
                    text = stringResource(Res.string.confirm_home_member_count).replace("n", "3"),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(9.dp)
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
                        text = "2026년 1월 2일",
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
                        text = "ABC123",
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

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
                        modifier = Modifier.size(15.dp),
                    )
                    
                    Text(
                        text = stringResource(Res.string.confirm_home_member_list_count).replace("n", "3"),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                tempMembers.sortedByDescending { it.isManager }.forEach {
                    UserSimpleInfo(it)
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))

            HomeNButton(
                text = stringResource(Res.string.home_entry_title),
                onClick = { viewModel.setEvent(JoinConfirmContract.Event.OnJoinClick) },
                enabled = true
            )
        }
    }
}

@Composable
fun UserSimpleInfo(user: User) {
    val painterResource = when (user.avatar) {
        0 -> painterResource(Res.drawable.chef_avatar)
        1 -> painterResource(Res.drawable.hero_avatar)
        2 -> painterResource(Res.drawable.guard_avatar)
        else -> painterResource(Res.drawable.chef_avatar)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BackgroundGray)
        ) {
            Icon(
                painter = painterResource,
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.Center),
                tint = Color.Unspecified
            )
        }

        Text(
            text = user.name,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 14.sp,
            color = Color.Black
        )

        if (user.isManager) {
            Box(
                modifier = Modifier
                    .background(color = Blue2, shape = RoundedCornerShape(13.dp))
                        .padding(vertical = 2.dp, horizontal = 5.dp)
            ) {
                Text(
                    text = "관리자",
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }
    }
}
