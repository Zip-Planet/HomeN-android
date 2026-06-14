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
import com.devndev.homen.core.common.util.Logger
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageContract
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chore_manage_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChoreManageScreen(
    viewModel: ChoreManageViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onNavToCreateChore: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ChoreManageContract.Effect.NavigateToBack -> onBackClick()
                ChoreManageContract.Effect.NavigateToCrateChore -> onNavToCreateChore()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(ChoreManageContract.Event.OnInit)
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.chore_manage_title),
                onBackClick = { viewModel.setEvent(ChoreManageContract.Event.OnBackClick) }
            )
        },
        mainIsLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            if (uiState.chores.isEmpty()) {
                 ChoreManageEmptyScreen(
                    uiState = uiState,
                    onTooltipClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnTooltipClick(it, true))
                    },
                    onSelectOption = {
                        viewModel.setEvent(ChoreManageContract.Event.OnOptionClick(it))
                    },
                    onNextButtonClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnNextButtonClick)
                    }
                )
            } else {
                ChoreManageNotEmptyScreen(
                    uiState = uiState,
                    onTooltipClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnTooltipClick(it, false))
                    }
                )
            }
        }
        if (uiState.isEmptyChoreTooltipShow && uiState.chores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            viewModel.setEvent(ChoreManageContract.Event.OnTooltipClick(
                                show = false,
                                isEmptyChore = true
                            ))
                        }
                    }
            )
        }

        if (uiState.isNotEmptyChoreTooltipShow && uiState.chores.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            viewModel.setEvent(ChoreManageContract.Event.OnTooltipClick(
                                show = false,
                                isEmptyChore = false
                            ))
                        }
                    }
            )
        }
    }
}
