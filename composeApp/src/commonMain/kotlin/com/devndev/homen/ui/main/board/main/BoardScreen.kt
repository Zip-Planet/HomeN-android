package com.devndev.homen.ui.main.board.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.board.main.viewmodel.BoardViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BoardScreen(
    viewModel: BoardViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {

                else -> {}
            }
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
        mainIsLoading = uiState.mainIsLoading
    ) {

    }
}