package com.devndev.homen.ui.main.board.exchange

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.board.exchange.viewmodel.BoardExchangeContract
import com.devndev.homen.ui.main.board.exchange.viewmodel.BoardExchangeViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board_exchange_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BoardExchangeScreen(
    viewModel: BoardExchangeViewModel = koinViewModel(),
    onNavBack: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(Unit) {
        viewModel.setEvent(BoardExchangeContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                BoardExchangeContract.Effect.NavigateToBack -> {
                    onNavBack()
                }
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.board_exchange_title),
                onBackClick = onNavBack
            )
        },
        mainIsLoading = uiState.mainIsLoading
    ) {
        BoardExchangeContent(
            weekDay = uiState.weekDay,
            myAssignments = uiState.myAssignments,
            targetAssignments = uiState.targetAssignments,
            onMyAssignmentSelected = {
                viewModel.setEvent(BoardExchangeContract.Event.OnMyAssignmentSelected(it))
            },
            onTargetAssignmentSelected = {
                viewModel.setEvent(BoardExchangeContract.Event.OnTargetAssignmentSelected(it))
            },
            isRequestable = uiState.selectedMyAssignment != null && uiState.selectedTargetAssignment != null,
            onExchangeMessageChanged = {
                viewModel.setEvent(BoardExchangeContract.Event.OnExchangeMessageChange(it))
            },
            exchangeMessage = uiState.exchangeMessage,
            onRequestExchangeClick = {
                viewModel.setEvent(BoardExchangeContract.Event.OnRequestExchangeClick)
            }
        )
    }
}
