package com.devndev.homen.ui.main.home.choredetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.RepeatDay
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.CompletedBy
import com.devndev.homen.core.domain.model.home.Memo
import com.devndev.homen.core.domain.model.home.WeeklyProgress
import com.devndev.homen.core.domain.model.home.WeeklyProgressStatus
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.choredetail.viewmodel.ChoreDetailContract
import com.devndev.homen.ui.main.home.choredetail.viewmodel.ChoreDetailViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.Blue4736FC
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.chore_detail_memo_add_btn
import homen.composeapp.generated.resources.chore_detail_memo_delete_snackbar_msg
import homen.composeapp.generated.resources.chore_detail_memo_title
import homen.composeapp.generated.resources.chore_detail_title
import homen.composeapp.generated.resources.chore_detail_weekly_progress_completed
import homen.composeapp.generated.resources.chore_detail_weekly_progress_incompleted
import homen.composeapp.generated.resources.chore_detail_weekly_progress_title
import homen.composeapp.generated.resources.chore_info_difficulty
import homen.composeapp.generated.resources.chore_info_point_days
import homen.composeapp.generated.resources.edit_alt_icon
import homen.composeapp.generated.resources.menu_dot_icon
import homen.composeapp.generated.resources.pin_black_icon
import homen.composeapp.generated.resources.snackbar_cancel
import homen.composeapp.generated.resources.star_black_icon
import homen.composeapp.generated.resources.trash_alt_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChoreDetailScreen(
    viewModel: ChoreDetailViewModel = koinViewModel(),
    choreId: Int,
    onBackClick: () -> Unit,
    onNavToMemo: (Int?, String?, Boolean) -> Unit
) {
    val uiState by viewModel.viewState
    val snackbarHostState = remember { SnackbarHostState() }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.setEvent(ChoreDetailContract.Event.OnDispose)
        }
    }

    BackHandler {
        viewModel.setEvent(ChoreDetailContract.Event.OnBackClick)
    }

    val snackbarMsg = stringResource(Res.string.chore_detail_memo_delete_snackbar_msg)
    val cancelMsg = stringResource(Res.string.snackbar_cancel)
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ChoreDetailContract.Effect.NavigateToBack -> {
                    onBackClick()
                }

                is ChoreDetailContract.Effect.NavigateToMemo -> {
                    onNavToMemo(effect.memoId, effect.content, effect.isEdit)
                }

                is ChoreDetailContract.Effect.ShowDeleteMemoSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = snackbarMsg,
                        actionLabel = cancelMsg,
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.setEvent(
                                ChoreDetailContract.Event.OnUndoDeleteMemo(
                                    memo = effect.memo,
                                    index = effect.index
                                )
                            )
                        }
                        SnackbarResult.Dismissed -> {
                            viewModel.setEvent(ChoreDetailContract.Event.OnDeleteConfirmMemo(effect.memo.id))
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(ChoreDetailContract.Event.OnInit(choreId))
    }

    ChoreDetailContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onNavToMemo = { memoId, content, isEdit ->
           viewModel.setEvent(ChoreDetailContract.Event.OnNavToMemo(memoId, content, isEdit))
        },
        onDeleteMemo = {
            viewModel.setEvent(ChoreDetailContract.Event.OnDeleteMemo(it))
        },
        onBackClick = { viewModel.setEvent(ChoreDetailContract.Event.OnBackClick) }
    )
}

@Composable
fun ChoreDetailContent(
    uiState: ChoreDetailContract.State,
    snackbarHostState: SnackbarHostState,
    onNavToMemo: (Int?, String?, Boolean) -> Unit,
    onDeleteMemo: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    var expandedMemoId by remember { mutableStateOf<Int?>(null) }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.chore_detail_title),
                onBackClick = { onBackClick() }
            )
        },
        mainIsLoading = uiState.isLoading,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { expandedMemoId = null })
                }
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.pin_black_icon),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = uiState.chore.name,
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ChoreDetailItem(
                chore = uiState.chore,
                onDeleteClick = {},
                onEditClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            WeeklyProgressSection(uiState = uiState)

            Spacer(modifier = Modifier.height(8.dp))

            MemoSection(
                memos = uiState.memos,
                expandedMemoId = expandedMemoId,
                onMenuClick = { id ->
                    expandedMemoId = if (expandedMemoId == id) null else id
                },
                onNavToMemo = onNavToMemo,
                onDeleteMemo = {
                    onDeleteMemo(it)
                }
            )
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ChoreDetailItem(
    chore: Chore,
    onDeleteClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val choreResource = ChoreCategory.fromId(chore.category).resource
    val infoFormat = stringResource(Res.string.chore_info_point_days)
    val diffFormat = stringResource(Res.string.chore_info_difficulty)
    val daysText = chore.repeatDays.joinToString(",") { dayValue ->
        when (dayValue) {
            RepeatDay.MONDAY.value -> RepeatDay.MONDAY.day
            RepeatDay.TUESDAY.value -> RepeatDay.TUESDAY.day
            RepeatDay.WEDNESDAY.value -> RepeatDay.WEDNESDAY.day
            RepeatDay.THURSDAY.value -> RepeatDay.THURSDAY.day
            RepeatDay.FRIDAY.value -> RepeatDay.FRIDAY.day
            RepeatDay.SATURDAY.value -> RepeatDay.SATURDAY.day
            RepeatDay.SUNDAY.value -> RepeatDay.SUNDAY.day
            else -> ""
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)

    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(choreResource),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.height(36.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(17.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Blue4)
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = HomeNTheme.typography.suitBold.toSpanStyle()) {
                            append(
                                infoFormat.replace("n", chore.difficulty.point.toString())
                                    .replace("s", daysText)
                            )
                        }
                        append(diffFormat.replace("s", chore.difficulty.label))
                    },
                    fontSize = 10.sp,
                    color = Color.Black,
                    style = HomeNTheme.typography.suitRegular
                )
            }
            Text(
                text = chore.description,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInHorizontally(
                    initialOffsetX = { it / 2 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { it / 2 },
                    animationSpec = tween(150)
                ) + fadeOut()

            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_alt_icon),
                        contentDescription = "edit chore icon",
                        modifier = Modifier.size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onEditClick(chore.id!!)
                            },
                    )
                    Icon(
                        painter = painterResource(Res.drawable.trash_alt_icon),
                        contentDescription = "delete chore icon",
                        modifier = Modifier.size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onDeleteClick(chore.id!!)
                            },
                    )
                }
            }
            Icon(
                painter = painterResource(Res.drawable.menu_dot_icon),
                contentDescription = "chore menu icon",
                modifier = Modifier.size(20.dp)
                    .background(color = BackgroundGray)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        isExpanded = !isExpanded
                    },
            )
        }
    }
}

@Composable
fun WeeklyProgressSection(
    uiState: ChoreDetailContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 15.dp)
            .padding(top = 20.dp, bottom = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.chart_icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(Res.string.chore_detail_weekly_progress_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        // TODO 임시값 서버 수정 완료시 변경
        val tempWeeklyProgress = listOf(
            WeeklyProgress(
                weekDay = 1,
                label = "월",
                status = WeeklyProgressStatus.COMPLETED,
                completedBy = CompletedBy(uid = "1", name = "투다리김치우동", profileImage = 1)
            ),
            WeeklyProgress(
                weekDay = 3,
                label = "수",
                status = WeeklyProgressStatus.INCOMPLETE,
                completedBy = CompletedBy(uid = "2", name = "아메리카노", profileImage = 2)
            )
        )
        tempWeeklyProgress.forEach { progress ->
            if (progress.completedBy != null && progress.status != WeeklyProgressStatus.NOT_SCHEDULED) {
                WeeklyProgressItem(progress)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
//        uiState.weeklyProgress?.forEach { progress ->
//            if (progress.completedBy != null && progress.status != WeeklyProgressStatus.NOT_SCHEDULED) {
//                WeeklyProgressItem(progress)
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//        }
    }
}

@Composable
fun WeeklyProgressItem(weeklyProgress: WeeklyProgress) {
    val profileResource = AvatarType.fromId(weeklyProgress.completedBy!!.profileImage).resource

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(26.dp),
                painter = painterResource(profileResource),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = RepeatDay.fromValue(weeklyProgress.weekDay).day + "요일",
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = Color.Black
            )

            Text(
                text = weeklyProgress.completedBy!!.name,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        when (weeklyProgress.status) {
            WeeklyProgressStatus.COMPLETED -> {
                Box(
                    modifier = Modifier
                        .height(17.dp)
                        .width(30.dp)
                        .background(color = Blue4736FC, shape = RoundedCornerShape(28.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.chore_detail_weekly_progress_completed),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }
            WeeklyProgressStatus.INCOMPLETE -> {
                Box(
                    modifier = Modifier
                        .height(17.dp)
                        .width(38.dp)
                        .background(color = ButtonGray, shape = RoundedCornerShape(28.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.chore_detail_weekly_progress_incompleted),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                }
            }
            WeeklyProgressStatus.NOT_SCHEDULED -> {

            }
        }
    }
}

@Composable
fun MemoSection(
    memos: List<Memo>,
    expandedMemoId: Int?,
    onMenuClick: (Int) -> Unit,
    onNavToMemo: (Int?, String?, Boolean) -> Unit,
    onDeleteMemo: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.star_black_icon),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = stringResource(Res.string.chore_detail_memo_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onNavToMemo(null, null, false)
                },
                text = stringResource(Res.string.chore_detail_memo_add_btn),
                style = HomeNTheme.typography.suitRegular.copy(textDecoration = TextDecoration.Underline),
                fontSize = 12.sp,
                color = Blue4736FC
            )
        }
        if (memos.isNotEmpty()) {
            Spacer(modifier = Modifier.height(15.dp))
        }
        memos.forEachIndexed { index, memo ->
            MemoItem(
                memo = memo,
                isExpanded = expandedMemoId == memo.id,
                onMenuClick = { onMenuClick(memo.id) },
                onNavToMemo = onNavToMemo,
                onDeleteMemo = {
                    onDeleteMemo(memo.id)
                }
            )
            if (index != memos.lastIndex) {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun MemoItem(
    memo: Memo,
    isExpanded: Boolean,
    onMenuClick: () -> Unit,
    onNavToMemo: (Int?, String?, Boolean) -> Unit,
    onDeleteMemo: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(end = 27.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = memo.content,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black
            )
            Text(
                text = memo.author.name,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = Color.Black
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(color = Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInHorizontally(
                    initialOffsetX = { it / 2 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { it / 2 },
                    animationSpec = tween(150)
                ) + fadeOut()

            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_alt_icon),
                        contentDescription = "edit chore icon",
                        modifier = Modifier.size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onNavToMemo(memo.id, memo.content, true)
                            },
                    )
                    Icon(
                        painter = painterResource(Res.drawable.trash_alt_icon),
                        contentDescription = "delete chore icon",
                        modifier = Modifier.size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onDeleteMemo()
                            },
                    )
                }
            }
            Icon(
                painter = painterResource(Res.drawable.menu_dot_icon),
                contentDescription = "chore menu icon",
                modifier = Modifier.size(20.dp)
                    .background(color = Color.White)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onMenuClick()
                    },
            )
        }
    }
}

@Preview
@Composable
fun ChoreDetailContentPreview() {
    ChoreDetailContent(
        uiState = ChoreDetailContract.State(
            chore = Chore(
                id = 0,
                category = 1,
                name = "분리수거 잘하기",
                description = "깔끔하게 잘해봐라",
                repeatDays = listOf(1,2,3),
                difficulty = ChoreDifficulty.LOW
            ),
            weeklyProgress = listOf(
                WeeklyProgress(
                    weekDay = 1,
                    label = "월",
                    status = WeeklyProgressStatus.COMPLETED,
                    completedBy = CompletedBy(uid = "1", name = "투다리김치우동", profileImage = 1)
                ),
                WeeklyProgress(
                    weekDay = 3,
                    label = "수",
                    status = WeeklyProgressStatus.INCOMPLETE,
                    completedBy = CompletedBy(uid = "2", name = "아메리카노", profileImage = 2)
                )
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onNavToMemo = { _, _, _ ->},
        onDeleteMemo = {},
        onBackClick = {},
    )
}