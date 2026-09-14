package com.devndev.homen.ui.main.board.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.board.GetBoardUseCase
import kotlinx.coroutines.launch

class BoardViewModel(
    private val getBoardUseCase: GetBoardUseCase
) : BaseViewModel<BoardContract.Event, BoardContract.State, BoardContract.Effect>() {
    override fun setInitialState() = BoardContract.State()

    override fun handleEvents(event: BoardContract.Event) {
        when (event) {
            BoardContract.Event.OnInit -> {
                getBoard()
            }

            BoardContract.Event.OnAssignmentClick -> {
                setEffect { BoardContract.Effect.NavigateToAssignment }
            }

            BoardContract.Event.OnRewardClick -> {
                setEffect { BoardContract.Effect.NavigateToReward }
            }
        }
    }

    private fun getBoard() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }
            val result = getBoardUseCase()
            when (result) {
                is ApiResult.Success -> {
                    setState {
                        copy(
                            cards = result.data.sortedByDescending { it.createdAt }
                        )
                    }
                }
                else -> {

                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }
}
