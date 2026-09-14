package com.devndev.homen.ui.main.board.help

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.board.help.viewmodel.BoardHelpContract
import com.devndev.homen.ui.main.board.help.viewmodel.BoardHelpViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board_help_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BoardHelpScreen(
    viewModel: BoardHelpViewModel = koinViewModel(),
    onNavBack: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(Unit) {
        viewModel.setEvent(BoardHelpContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                BoardHelpContract.Effect.NavigateToBack -> {
                    onNavBack()
                }
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.board_help_title),
                onBackClick = onNavBack
            )
        },
        mainIsLoading = uiState.mainIsLoading
    ) {
        BoardHelpContent(
            weekDay = uiState.weekDay,
            assignments = uiState.assignments,
            onAssignmentSelected = {
                viewModel.setEvent(BoardHelpContract.Event.OnAssignmentSelected(it))
            },
            isRequestable = uiState.selectedAssignment != null,
            onHelpMessageChanged = {
                viewModel.setEvent(BoardHelpContract.Event.OnHelpMessageChange(it))
            },
            helpMessage = uiState.helpMessage
        )
    }
}