package com.devndev.homen.ui.main.home.choremanage

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageContract
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chore_delete_snackbar_msg
import homen.composeapp.generated.resources.chore_manage_title
import homen.composeapp.generated.resources.snackbar_cancel
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChoreManageScreen(
    viewModel: ChoreManageViewModel = koinViewModel(),
    deletedChoreId: Int? = null,
    onDeleteConsumed: () -> Unit = {},
    onBackClick: () -> Unit,
    onNavToCreateChore: () -> Unit,
    onNavToEditChore: (Int) -> Unit,
    onNavToChoreDetail: (Int) -> Unit,
    onNavToStaterPack: () -> Unit
) {
    val uiState by viewModel.viewState
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(deletedChoreId) {
        deletedChoreId?.let { id ->
            viewModel.setEvent(ChoreManageContract.Event.OnDeleteClick(id))
            onDeleteConsumed()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.setEvent(ChoreManageContract.Event.OnDispose)
        }
    }

    BackHandler {
        viewModel.setEvent(ChoreManageContract.Event.OnBackClick)
    }

    val deleteMsg = stringResource(Res.string.chore_delete_snackbar_msg)
    val cancelMsg = stringResource(Res.string.snackbar_cancel)
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ChoreManageContract.Effect.NavigateToBack -> onBackClick()
                ChoreManageContract.Effect.NavigateToCrateChore -> onNavToCreateChore()
                is ChoreManageContract.Effect.NavigateToEditChore -> onNavToEditChore(effect.id)
                is ChoreManageContract.Effect.NavigateToChoreDetail -> onNavToChoreDetail(effect.id)
                ChoreManageContract.Effect.NavigateToStaterPack -> onNavToStaterPack()
                is ChoreManageContract.Effect.ShowDeleteSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = deleteMsg,
                        actionLabel = cancelMsg,
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.setEvent(
                                ChoreManageContract.Event.OnUndoDelete(
                                    chore = effect.chore,
                                    index = effect.index
                                )
                            )
                        }
                        SnackbarResult.Dismissed -> {
                            viewModel.setEvent(ChoreManageContract.Event.OnDeleteConfirm(effect.chore.id!!))
                        }
                    }
                }
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
        mainIsLoading = uiState.isLoading,
        snackbarHost = { 
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 34.dp)
            ) 
        },
        isNeedBottomExpanded = uiState.chores.isNotEmpty()
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
                    },
                    onAddButtonClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnAddButtonClick)
                    },
                    onDeleteClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnDeleteClick(it))
                    },
                    onEditClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnEditClick(it))
                    },
                    onChoreClick = {
                        viewModel.setEvent(ChoreManageContract.Event.OnChoreClick(it))
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
