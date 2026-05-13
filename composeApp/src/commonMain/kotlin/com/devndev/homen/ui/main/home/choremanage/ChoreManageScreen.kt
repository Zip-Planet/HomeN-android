package com.devndev.homen.ui.main.home.choremanage

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageContract
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home_entry_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChoreManageScreen(
    viewModel: ChoreManageViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ChoreManageContract.Effect.NavigateToBack -> onBackClick()
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_entry_title),
                onBackClick = { viewModel.setEvent(ChoreManageContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            if (uiState.isEmptyChore) {
                ChoreManageEmptyScreen(
                    uiState = uiState,
                    onTooltipClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnTooltipClick(it))
                    },
                    onSelectOption = {
                        viewModel.setEvent(ChoreManageContract.Event.OnOptionClick(it))
                    },
                    onNextButtonClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnNextButtonClick)
                    }
                )
            } else {
                // TODO 집안일 관리
            }
        }
        if (uiState.isEmptyChoreTooltipShow) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            viewModel.setEvent(ChoreManageContract.Event.OnTooltipClick(false))
                        }
                    }
            )
        }
    }
}
