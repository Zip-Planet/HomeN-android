package com.devndev.homen.ui.main.board.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.board.AcceptExchangeUseCase
import com.devndev.homen.core.domain.usecase.board.AcceptHelpUseCase
import com.devndev.homen.core.domain.usecase.board.DeleteExchangeUseCase
import com.devndev.homen.core.domain.usecase.board.DeleteHelpUseCase
import com.devndev.homen.core.domain.usecase.board.GetBoardUseCase
import com.devndev.homen.core.domain.usecase.board.RejectExchangeUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.core.domain.model.board.BoardMember
import com.devndev.homen.core.domain.model.board.ExchangeBoardType
import com.devndev.homen.core.domain.model.board.HelpBoardType
import com.devndev.homen.ui.main.board.main.viewmodel.BoardContract.Effect.*
import kotlinx.coroutines.launch

class BoardViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getBoardUseCase: GetBoardUseCase,
    private val deleteHelpUseCase: DeleteHelpUseCase,
    private val acceptHelpUseCase: AcceptHelpUseCase,
    private val deleteExchangeUseCase: DeleteExchangeUseCase,
    private val acceptExchangeUseCase: AcceptExchangeUseCase,
    private val rejectExchangeUseCase: RejectExchangeUseCase,
) : BaseViewModel<BoardContract.Event, BoardContract.State, BoardContract.Effect>() {
    override fun setInitialState() = BoardContract.State()

    private var pendingDeleteId: Int? = null
    private var pendingDeleteExchangeId: Int? = null
    private var pendingAcceptId: Int? = null
    private var pendingExchangeAcceptId: Int? = null
    private var pendingExchangeRejectId: Int? = null

    override fun handleEvents(event: BoardContract.Event) {
        when (event) {
            BoardContract.Event.OnInit -> {
                getBoard()
            }

            BoardContract.Event.OnAssignmentClick -> {
                setEffect { NavigateToAssignment }
            }

            BoardContract.Event.OnRewardClick -> {
                setEffect { NavigateToReward }
            }

            BoardContract.Event.OnRequestHelpClick -> {
                setEffect { NavigateToHelp }
            }

            BoardContract.Event.OnExchangeClick -> {
                setEffect { NavigateToExchange }
            }

            is BoardContract.Event.OnDeleteHelpClick -> {
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    pendingDeleteId = event.id
                    val card = viewState.value.cards[index]
                    val updatedCards = viewState.value.cards.toMutableList().apply { removeAt(index) }
                    setState { copy(cards = updatedCards) }
                    setEffect { ShowDeleteSnackBar(card, index) }
                }
            }

            is BoardContract.Event.OnDeleteExchangeClick -> {
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    pendingDeleteExchangeId = event.id
                    val card = viewState.value.cards[index]
                    val updatedCards = viewState.value.cards.toMutableList().apply { removeAt(index) }
                    setState { copy(cards = updatedCards) }
                    setEffect { ShowDeleteExchangeSnackBar(card, index) }
                }
            }

            is BoardContract.Event.OnUndoDelete -> {
                pendingDeleteId = null
                pendingDeleteExchangeId = null
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
                pendingDeleteId = null
                deleteHelp(event.id)
            }

            is BoardContract.Event.OnDeleteExchangeConfirm -> {
                pendingDeleteExchangeId = null
                deleteExchange(event.id)
            }

            is BoardContract.Event.OnAcceptHelp -> {
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    pendingAcceptId = event.id
                    val card = viewState.value.cards[index]
                    val oldStatus = card.status ?: HelpBoardType.PENDING.type

                    // 낙관적 업데이트: 기존 요청 카드를 제거하고, 수락 완료 카드를 해당 위치에 추가 (교체)
                    val acceptedCard = card.copy(
                        status = HelpBoardType.ACCEPTED.type,
                        acceptedBy = BoardMember(
                            uid = "",
                            name = viewState.value.myName,
                            profileImage = viewState.value.myProfileImage
                        )
                    )
                    val updatedCards = viewState.value.cards.toMutableList().apply {
                        removeAt(index)
                        add(index, acceptedCard)
                    }
                    setState { copy(cards = updatedCards) }
                    setEffect { ShowAcceptSnackBar(event.id, oldStatus) }
                }
            }

            is BoardContract.Event.OnUndoAccept -> {
                pendingAcceptId = null
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    val card = viewState.value.cards[index]
                    val updatedCard = card.copy(status = event.oldStatus, acceptedBy = null)
                    val updatedCards = viewState.value.cards.toMutableList().apply {
                        set(index, updatedCard)
                    }
                    setState { copy(cards = updatedCards) }
                }
            }

            is BoardContract.Event.OnAcceptConfirm -> {
                pendingAcceptId = null
                acceptHelp(event.id)
            }

            is BoardContract.Event.OnAcceptExchange -> {
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    pendingExchangeAcceptId = event.id
                    val card = viewState.value.cards[index]
                    val oldStatus = card.status ?: ExchangeBoardType.PENDING.type

                    val updatedCard = card.copy(
                        status = ExchangeBoardType.ACCEPTED.type,
                        respondedBy = BoardMember(
                            uid = "",
                            name = viewState.value.myName,
                            profileImage = viewState.value.myProfileImage
                        )
                    )
                    val updatedCards = viewState.value.cards.toMutableList().apply {
                        set(index, updatedCard)
                    }
                    setState { copy(cards = updatedCards) }
                    setEffect { ShowExchangeAcceptSnackBar(event.id, oldStatus) }
                }
            }

            is BoardContract.Event.OnRejectExchange -> {
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    pendingExchangeRejectId = event.id
                    val card = viewState.value.cards[index]
                    val oldStatus = card.status ?: ExchangeBoardType.PENDING.type

                    val updatedCard = card.copy(
                        status = ExchangeBoardType.REJECTED.type
                    )
                    val updatedCards = viewState.value.cards.toMutableList().apply {
                        set(index, updatedCard)
                    }
                    setState { copy(cards = updatedCards) }
                    setEffect { ShowExchangeRejectSnackBar(event.id, oldStatus) }
                }
            }

            is BoardContract.Event.OnUndoExchangeResponse -> {
                pendingExchangeAcceptId = null
                pendingExchangeRejectId = null
                val index = viewState.value.cards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    val card = viewState.value.cards[index]
                    val updatedCard = card.copy(status = event.oldStatus, respondedBy = null)
                    val updatedCards = viewState.value.cards.toMutableList().apply {
                        set(index, updatedCard)
                    }
                    setState { copy(cards = updatedCards) }
                }
            }

            is BoardContract.Event.OnConfirmExchangeAccept -> {
                pendingExchangeAcceptId = null
                acceptExchange(event.id)
            }

            is BoardContract.Event.OnConfirmExchangeReject -> {
                pendingExchangeRejectId = null
                rejectExchange(event.id)
            }

            BoardContract.Event.OnDispose -> {
                pendingDeleteId?.let { deleteHelp(it) }
                pendingDeleteExchangeId?.let { deleteExchange(it) }
                pendingAcceptId?.let { acceptHelp(it) }
                pendingExchangeAcceptId?.let { acceptExchange(it) }
                pendingExchangeRejectId?.let { rejectExchange(it) }
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
                        myProfileImage = myInfoResult.data.profileImage,
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

    private fun deleteExchange(id: Int) {
        viewModelScope.launch {
            deleteExchangeUseCase(id)
            // 에러 처리 필요시 여기에 추가
        }
    }

    private fun acceptHelp(id: Int) {
        viewModelScope.launch {
            acceptHelpUseCase(id)
        }
    }

    private fun acceptExchange(id: Int) {
        viewModelScope.launch {
            acceptExchangeUseCase(id)
        }
    }

    private fun rejectExchange(id: Int) {
        viewModelScope.launch {
            rejectExchangeUseCase(id)
        }
    }
}
