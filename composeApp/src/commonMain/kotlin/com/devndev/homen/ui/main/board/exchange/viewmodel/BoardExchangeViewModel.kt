package com.devndev.homen.ui.main.board.exchange.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.board.CreateExchangeUseCase
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch
import kotlin.time.Clock

class BoardExchangeViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentUseCase: GetAssignmentsUseCase,
    private val createExchangeUseCase: CreateExchangeUseCase
) : BaseViewModel<BoardExchangeContract.Event, BoardExchangeContract.State, BoardExchangeContract.Effect>() {
    override fun setInitialState() = BoardExchangeContract.State()

    override fun handleEvents(event: BoardExchangeContract.Event) {
        when (event) {
            BoardExchangeContract.Event.OnInit -> {
                initData()
            }

            is BoardExchangeContract.Event.OnMyAssignmentSelected -> {
                setState { copy(selectedMyAssignment = event.assignment) }
            }

            is BoardExchangeContract.Event.OnTargetAssignmentSelected -> {
                setState { copy(selectedTargetAssignment = event.assignment) }
            }

            is BoardExchangeContract.Event.OnExchangeMessageChange -> {
                setState { copy(exchangeMessage = event.message) }
            }

            BoardExchangeContract.Event.OnRequestExchangeClick -> {
                createExchange()
            }
        }
    }

    private fun initData() {
        val currentTime = Clock.System.now().toString()
        setState {
            copy(
                weekDay = DateUtil.formatWeekOfMonth(currentTime)
            )
        }
        getAssignments()
    }

    private fun getAssignments() {
        setState { copy(mainIsLoading = true) }
        viewModelScope.launch {
            val myInfoResult = getMyInfoUseCase()
            val assignmentResult = getAssignmentUseCase(DateUtil.getThisWeekMonday())

            if (myInfoResult is ApiResult.Success && assignmentResult is ApiResult.Success) {
                val myUid = myInfoResult.data.uid
                val myAssignments = assignmentResult.data.items.filter {
                    it.assignee?.uid == myUid && !it.isCompleted
                }
                val targetAssignments = assignmentResult.data.items.filter {
                    it.assignee?.uid != myUid && !it.isCompleted
                }
                setState {
                    copy(
                        myAssignments = myAssignments,
                        targetAssignments = targetAssignments
                    )
                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun createExchange() {
        val myAssignmentId = viewState.value.selectedMyAssignment?.id ?: return
        val targetAssignmentId = viewState.value.selectedTargetAssignment?.id ?: return
        
        viewModelScope.launch {
            val result = createExchangeUseCase(
                requesterItemId = myAssignmentId,
                targetItemId = targetAssignmentId,
                message = viewState.value.exchangeMessage
            )
            when (result) {
                is ApiResult.Success -> {
                    setEffect { BoardExchangeContract.Effect.NavigateToBack }
                }
                else -> {
                    // Handle error
                }
            }
        }
    }
}
