package com.devndev.homen.ui.main.board.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devndev.homen.OsType
import com.devndev.homen.core.domain.model.board.BoardPayload
import com.devndev.homen.core.domain.model.board.BoardType
import com.devndev.homen.core.domain.model.board.BotType
import com.devndev.homen.core.domain.model.board.HelpBoardType
import com.devndev.homen.getPlatform
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.board.component.BoardDateSeparator
import com.devndev.homen.ui.main.board.component.BoardFloatingActionButton
import com.devndev.homen.ui.main.board.component.DivisionPlanMessage
import com.devndev.homen.ui.main.board.component.HelpAcceptMessage
import com.devndev.homen.ui.main.board.component.HelpMessage
import com.devndev.homen.ui.main.board.component.RewardMessage
import com.devndev.homen.ui.main.board.main.viewmodel.BoardContract
import com.devndev.homen.ui.main.board.main.viewmodel.BoardViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.util.DateUtil
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board
import homen.composeapp.generated.resources.board_help_accept_snackbar_msg
import homen.composeapp.generated.resources.board_help_delete_snackbar_msg
import homen.composeapp.generated.resources.snackbar_cancel
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BoardScreen(
    viewModel: BoardViewModel = koinViewModel(),
    onNavToReward: () -> Unit,
    onNavToAssignment: () -> Unit,
    onNavToHelp: () -> Unit,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.viewState
    val snackbarHostState = remember { SnackbarHostState() }

    val deleteMsg = stringResource(Res.string.board_help_delete_snackbar_msg)
    val acceptMsg = stringResource(Res.string.board_help_accept_snackbar_msg)
    val cancelMsg = stringResource(Res.string.snackbar_cancel)
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                BoardContract.Effect.NavigateToAssignment -> {
                    onNavToAssignment()
                }
                BoardContract.Effect.NavigateToReward -> {
                    onNavToReward()
                }

                BoardContract.Effect.NavigateToHelp -> {
                    onNavToHelp()
                }

                is BoardContract.Effect.ShowDeleteSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = deleteMsg,
                        actionLabel = cancelMsg,
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.setEvent(
                                BoardContract.Event.OnUndoDelete(
                                    card = effect.card,
                                    index = effect.index
                                )
                            )
                        }

                        SnackbarResult.Dismissed -> {
                            viewModel.setEvent(BoardContract.Event.OnDeleteConfirm(effect.card.id))
                        }
                    }
                }

                is BoardContract.Effect.ShowAcceptSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = acceptMsg,
                        actionLabel = cancelMsg,
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.setEvent(BoardContract.Event.OnUndoAccept(effect.id, effect.oldStatus))
                        }

                        SnackbarResult.Dismissed -> {
                            viewModel.setEvent(BoardContract.Event.OnAcceptConfirm(effect.id))
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(BoardContract.Event.OnInit)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.setEvent(BoardContract.Event.OnDispose)
        }
    }

    HomeNScreen(
        topBar = {
            NotificationTopBar(
                title = stringResource(Res.string.board),
                onNotificationClick = {}
            )
        },
        isLoading = uiState.isLoading,
        mainIsLoading = uiState.mainIsLoading,
        snackbarHost = {
            val snackbarBottomPadding = if (getPlatform() == OsType.IOS) 34 else 94

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = snackbarBottomPadding.dp)
            )
        },
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
                            when (card.status) {
                                HelpBoardType.PENDING.type -> {
                                    val isMine = uiState.myName == card.requester?.name
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart
                                    ) {
                                        HelpMessage(
                                            isMine = isMine,
                                            name = card.requester?.name ?: "",
                                            description = card.message?: "",
                                            date = card.createdAt,
                                            item = card.item!!,
                                            onClick = {
                                                if (isMine) {
                                                    viewModel.setEvent(BoardContract.Event.OnDeleteHelpClick(card.id))
                                                } else {
                                                    viewModel.setEvent(BoardContract.Event.OnAcceptHelp(card.id))
                                                }
                                            }
                                        )
                                    }

                                }

                                HelpBoardType.ACCEPTED.type -> {
                                    HelpAcceptMessage(
                                        requester = card.requester!!,
                                        acceptedBy = card.acceptedBy!!,
                                        date = card.createdAt,
                                        item = card.item!!
                                    )
                                }

                                HelpBoardType.EXPIRED.type -> {

                                }
                            }
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
                onHelpClick = {
                    viewModel.setEvent(BoardContract.Event.OnRequestHelpClick)
                },
                paddingValues = paddingValues
            )
        }
    }
}
