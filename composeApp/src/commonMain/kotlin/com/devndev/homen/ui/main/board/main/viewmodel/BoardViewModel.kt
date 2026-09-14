package com.devndev.homen.ui.main.board.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.board.DeleteHelpUseCase
import com.devndev.homen.core.domain.usecase.board.GetBoardUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import kotlinx.coroutines.launch

class BoardViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getBoardUseCase: GetBoardUseCase,
    private val deleteHelpUseCase: DeleteHelpUseCase
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

            BoardContract.Event.OnRequestHelpClick -> {
                setEffect { BoardContract.Effect.NavigateToHelp }
            }

            is BoardContract.Event.OnDeleteHelpClick -> {
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    val card = viewState.value.cards[index]
                    val updatedCards = viewState.value.cards.toMutableList().apply { removeAt(index) }
                    setState { copy(cards = updatedCards) }
                    setEffect { BoardContract.Effect.ShowDeleteSnackBar(card, index) }
                }
            }

            is BoardContract.Event.OnUndoDelete -> {
                val updatedCards = viewState.value.cards.toMutableList().apply { 
                    if (event.index <= size) {
                        add(event.index, event.card)
                    } else {
                        add(event.card)
                    }
                }
                setState { copy(cards = updatedCards) }
            }

            is BoardContract.Event.OnDeleteConfirm -> {
                deleteHelp(event.id)
            }
        }
    }

    private fun getBoard() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }
            val myInfoResult = getMyInfoUseCase()
            val result = getBoardUseCase()
            if (myInfoResult is ApiResult.Success && result is ApiResult.Success) {
                setState {
                    copy(
                        myName = myInfoResult.data.name,
                        cards = result.data.sortedByDescending { it.createdAt }
                    )
                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun deleteHelp(id: Int) {
        viewModelScope.launch {
            deleteHelpUseCase(id)
            // 에러 처리 필요시 여기에 추가
        }
    }
}
