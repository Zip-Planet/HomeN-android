package com.devndev.homen.ui.main.board.help.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch
import kotlin.time.Clock

class BoardHelpViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentUseCase: GetAssignmentsUseCase
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
                setState { copy(helpMessage = event.message)}
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
}