package com.devndev.homen.ui.main.board.help.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.board.CreateHelpUseCase
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch
import kotlin.time.Clock

class BoardHelpViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentUseCase: GetAssignmentsUseCase,
    private val createHelpUseCase: CreateHelpUseCase
) :
    BaseViewModel<BoardHelpContract.Event, BoardHelpContract.State, BoardHelpContract.Effect>() {
    override fun setInitialState() = BoardHelpContract.State()

    override fun handleEvents(event: BoardHelpContract.Event) {
        when (event) {
            BoardHelpContract.Event.OnInit -> {
                initData()
            }

            is BoardHelpContract.Event.OnAssignmentSelected -> {
                setState { copy(selectedAssignment = event.assignment) }
            }

            is BoardHelpContract.Event.OnHelpMessageChange -> {
                setState { copy(helpMessage = event.message) }
            }

            BoardHelpContract.Event.OnRequestHelpClick -> {
                createHelp()
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
                val filteredAssignments = assignmentResult.data.items.filter {
                    it.assignee?.uid == myUid && !it.isCompleted
                }
                setState {
                    copy(assignments = filteredAssignments)
                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun createHelp() {
        viewModelScope.launch {
            val result = createHelpUseCase(
                itemId = viewState.value.selectedAssignment!!.id,
                message = viewState.value.helpMessage
            )
            when (result) {
                is ApiResult.Success<*> -> {
                    setEffect { BoardHelpContract.Effect.NavigateToBack }
                }

                else -> {

                }

            }
        }
    }
}