package com.devndev.homen.ui.main.board.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devndev.homen.core.domain.model.board.BoardPayload
import com.devndev.homen.core.domain.model.board.BoardType
import com.devndev.homen.core.domain.model.board.BotType
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.board.component.BoardDateSeparator
import com.devndev.homen.ui.main.board.component.BoardFloatingActionButton
import com.devndev.homen.ui.main.board.component.DivisionPlanMessage
import com.devndev.homen.ui.main.board.component.RewardMessage
import com.devndev.homen.ui.main.board.main.viewmodel.BoardContract
import com.devndev.homen.ui.main.board.main.viewmodel.BoardViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.util.DateUtil
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BoardScreen(
    viewModel: BoardViewModel = koinViewModel(),
    onNavToReward: () -> Unit,
    onNavToAssignment: () -> Unit,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                BoardContract.Effect.NavigateToAssignment -> {
                    onNavToAssignment()
                }
                BoardContract.Effect.NavigateToReward -> {
                    onNavToReward()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(BoardContract.Event.OnInit)
    }

    HomeNScreen(
        topBar = {
            NotificationTopBar(
                title = stringResource(Res.string.board),
                onNotificationClick = {}
            )
        },
        isLoading = uiState.isLoading,
        mainIsLoading = uiState.mainIsLoading
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                    .padding(top = 26.dp),
                reverseLayout = true
            ) {
                item {
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
                }

                itemsIndexed(uiState.cards) { index, card ->
                    // 카드 표시
                    when (card.type) {
                        BoardType.BOT.type -> {
                            when (card.kind) {
                                BotType.REWARD.type -> {
                                    RewardMessage(
                                        date = card.createdAt,
                                        reward = card.payload as BoardPayload.Reward,
                                        onClick = {
                                            viewModel.setEvent(BoardContract.Event.OnRewardClick)
                                        }
                                    )
                                }

                                BotType.ASSIGNMENT_CREATED.type,
                                BotType.ASSIGNMENT_CONFIRMED.type -> {
                                    DivisionPlanMessage(
                                        date = card.createdAt,
                                        botType = card.kind!!,
                                        assignment = card.payload as BoardPayload.Assignment,
                                        onClick = {
                                            viewModel.setEvent(BoardContract.Event.OnAssignmentClick)
                                        }
                                    )
                                }
                            }
                        }

                        BoardType.REQUEST_HELP.type -> {
                            // TODO
                        }

                        BoardType.REQUEST_EXCHANGE.type -> {
                            // TODO
                        }
                    }

                    // 주차 구분선 표시 (위로 쌓이므로 현재 카드 위에 표시됨)
                    val currentWeek = DateUtil.formatWeekOfMonth(card.createdAt)
                    val nextCard = uiState.cards.getOrNull(index + 1)
                    val nextWeek = nextCard?.let { DateUtil.formatWeekOfMonth(it.createdAt) }

                    if (currentWeek != nextWeek) {
                        BoardDateSeparator(date = currentWeek)
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            BoardFloatingActionButton(
                onExchangeClick = {},
                onHelpClick = {},
                paddingValues = paddingValues
            )
        }
    }
}
