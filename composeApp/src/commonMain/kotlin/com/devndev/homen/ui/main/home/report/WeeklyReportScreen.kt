package com.devndev.homen.ui.main.home.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.report.viewmodel.WeeklyReportContract
import com.devndev.homen.ui.main.home.report.viewmodel.WeeklyReportViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue1E6EF4
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.Blue4736FC
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.Gray7C
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.award_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.good_hand_icon
import homen.composeapp.generated.resources.pin_black_icon
import homen.composeapp.generated.resources.user_plus
import homen.composeapp.generated.resources.warning_icon
import homen.composeapp.generated.resources.weekly_report_completed_count
import homen.composeapp.generated.resources.weekly_report_completed_ratio
import homen.composeapp.generated.resources.weekly_report_edit_chores_btn
import homen.composeapp.generated.resources.weekly_report_empty_msg
import homen.composeapp.generated.resources.weekly_report_empty_title
import homen.composeapp.generated.resources.weekly_report_highlight_title
import homen.composeapp.generated.resources.weekly_report_most_done_label
import homen.composeapp.generated.resources.weekly_report_most_missed_label
import homen.composeapp.generated.resources.weekly_report_mvp_title
import homen.composeapp.generated.resources.weekly_report_progress_title
import homen.composeapp.generated.resources.weekly_report_rank_title
import homen.composeapp.generated.resources.weekly_report_suggestion_msg
import homen.composeapp.generated.resources.weekly_report_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeeklyReportScreen(
    viewModel: WeeklyReportViewModel = koinViewModel(),
    onNavBack: () -> Unit,
    onNavToChoreManage: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(Unit) {
        viewModel.setEvent(WeeklyReportContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                WeeklyReportContract.Effect.PopBackStack -> {
                    onNavBack()
                }

                WeeklyReportContract.Effect.NavigateToChoreManage -> {
                    onNavToChoreManage()
                }
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.weekly_report_title),
                onBackClick = { viewModel.setEvent(WeeklyReportContract.Event.OnBackClick) }
            )
        },
        mainIsLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(27.dp))

            if (!uiState.hasReport || uiState.weeklyReport == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(vertical = 20.dp, horizontal = 15.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.clipboard_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                            )
                            Text(
                                text = stringResource(Res.string.weekly_report_empty_title),
                                style = HomeNTheme.typography.suitExtraBold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                        Text(
                            text = stringResource(Res.string.weekly_report_empty_msg),
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 14.sp,
                            color = Gray7C
                        )
                    }
                }
            } else {
                val report = uiState.weeklyReport!!

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.pin_black_icon),
                            contentDescription = null,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = stringResource(Res.string.weekly_report_progress_title),
                            style = HomeNTheme.typography.suitExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "${report.progressRate}%",
                            style = HomeNTheme.typography.suitExtraBold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                    }

                    Spacer(modifier = Modifier.height(13.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(14.dp)
                                .background(ButtonGray, RoundedCornerShape(99.dp))
                                .padding(3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(
                                        fraction = (report.progressRate.coerceIn(
                                            0,
                                            100
                                        ) / 100f)
                                    )
                                    .background(Blue4736FC, RoundedCornerShape(99.dp))
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Blue4736FC)) {
                                    append("${report.completedCount}")
                                }
                                withStyle(style = SpanStyle(color = BottomGray)) {
                                    append("/${report.totalCount}")
                                }
                            },
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 14.sp
                        )
                    }

                    // Section 2: 우리집 MVP
                    val mvp = report.mvp
                    if (mvp != null) {
                        Spacer(modifier = Modifier.height(15.dp))
                        val avatarRes = AvatarType.fromId(mvp.profileImage ?: 1).resource
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .padding(vertical = 20.dp, horizontal = 15.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.award_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp)
                                )

                                Text(
                                    text = stringResource(Res.string.weekly_report_mvp_title),
                                    style = HomeNTheme.typography.suitBold,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(BackgroundGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(avatarRes),
                                        contentDescription = null,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                val completedText =
                                    mvp.completedCount?.let {
                                        stringResource(Res.string.weekly_report_completed_count).replace(
                                            "s",
                                            it.toString()
                                        )
                                    } ?: "완료"
                                Box(
                                    modifier = Modifier
                                        .height(17.dp)
                                        .background(
                                            color = Blue4,
                                            shape = RoundedCornerShape(28.dp)
                                        )
                                        .padding(horizontal = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = completedText,
                                        style = HomeNTheme.typography.suitBold,
                                        fontSize = 10.sp,
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(9.dp))
                                Text(
                                    text = mvp.name,
                                    style = HomeNTheme.typography.suitBold,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "${mvp.point ?: 0}P",
                                    style = HomeNTheme.typography.suitRegular,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                        }
                    }

                    // Section 3: 누가 가장 많이 달성했을까요?
                    if (report.memberStats.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .padding(vertical = 20.dp, horizontal = 15.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.user_plus),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                )

                                Text(
                                    text = stringResource(Res.string.weekly_report_rank_title),
                                    style = HomeNTheme.typography.suitBold,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                            report.memberStats.forEach { stat ->
                                val avatarRes =
                                    AvatarType.fromId(stat.profileImage ?: 1).resource
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(BackgroundGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(avatarRes),
                                            contentDescription = null,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = stat.name,
                                        style = HomeNTheme.typography.suitBold,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .height(17.dp)
                                            .background(
                                                color = BackgroundGray,
                                                shape = RoundedCornerShape(28.dp)
                                            )
                                            .padding(horizontal = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(Res.string.weekly_report_completed_ratio).replace(
                                                "s1",
                                                stat.completedCount.toString()
                                            ).replace("s2", stat.assignedCount.toString()),
                                            style = HomeNTheme.typography.suitBold,
                                            fontSize = 10.sp,
                                            color = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "${stat.point}P",
                                        style = HomeNTheme.typography.suitRegular,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    // Section 4: 이번주 하이라이트
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(vertical = 20.dp, horizontal = 15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.pin_black_icon),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )

                            Text(
                                text = stringResource(Res.string.weekly_report_highlight_title),
                                style = HomeNTheme.typography.suitBold,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        val mostDone = report.mostDone
                        if (mostDone != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clip(CircleShape)
                                        .background(BackgroundGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.good_hand_icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = stringResource(Res.string.weekly_report_most_done_label),
                                    style = HomeNTheme.typography.suitBold,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "${mostDone.name} · ${mostDone.count}회",
                                    style = HomeNTheme.typography.suitRegular,
                                    fontSize = 13.sp,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        val mostMissed = report.mostMissed
                        if (mostMissed != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(23.dp)
                                        .clip(CircleShape)
                                        .background(BackgroundGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(Res.drawable.warning_icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = stringResource(Res.string.weekly_report_most_missed_label),
                                    style = HomeNTheme.typography.suitBold,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = "${mostMissed.name} · ${mostMissed.count}회",
                                    style = HomeNTheme.typography.suitRegular,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(37.dp))

                        Text(
                            text = stringResource(Res.string.weekly_report_suggestion_msg),
                            style = HomeNTheme.typography.suitRegular,
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        HomeNButton(
                            text = stringResource(Res.string.weekly_report_edit_chores_btn),
                            onClick = {

                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
