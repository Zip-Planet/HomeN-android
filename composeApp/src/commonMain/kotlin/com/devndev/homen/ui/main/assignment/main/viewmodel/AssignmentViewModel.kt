package com.devndev.homen.ui.main.assignment.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.usecase.home.ConfirmAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.CreateAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoresUseCase
import com.devndev.homen.core.domain.usecase.home.RegenerateAssignmentUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class AssignmentViewModel(
    private val getChoresUseCase: GetChoresUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentsUseCase: GetAssignmentsUseCase,
    private val createAssignmentUseCase: CreateAssignmentUseCase,
    private val confirmAssignmentUseCase: ConfirmAssignmentUseCase,
    private val regenerateAssignmentUseCase: RegenerateAssignmentUseCase
) : BaseViewModel<AssignmentContract.Event, AssignmentContract.State, AssignmentContract.Effect>() {
    override fun setInitialState() = AssignmentContract.State()
    override fun handleEvents(event: AssignmentContract.Event) {
        when (event) {
            AssignmentContract.Event.OnInit -> {
                getAssignmentData(DateUtil.getThisWeekMonday(), isInit = true)
            }

            is AssignmentContract.Event.OnTabSelected -> {
                setState { copy(selectedTab = event.tab, screenType = AssignmentScreenType.NONE) }

                when (event.tab) {
                    AssignmentTab.THIS_WEEK -> {
                        setState { copy(weekOffset = 1) }
                        getAssignmentData(DateUtil.getThisWeekMonday())
                    }
                    AssignmentTab.HISTORY -> {
                        getAssignmentData(DateUtil.getMondayOfWeek(-viewState.value.weekOffset))
                    }
                    AssignmentTab.NEXT_WEEK -> {
                        setState { copy(weekOffset = 1) }
                        getAssignmentData(DateUtil.getNextWeekMonday())
                    }

                }
            }

            is AssignmentContract.Event.OnSelectedMember -> {
                onMemberSelected(event.member)
            }

            AssignmentContract.Event.OnAddChoreClick -> {
                setEffect { AssignmentContract.Effect.NavigateToChoreManage }
            }

            AssignmentContract.Event.OnCreateAssignmentClick -> {
                when (viewState.value.selectedTab) {
                    AssignmentTab.THIS_WEEK -> createAssignment(DateUtil.getThisWeekMonday())
                    AssignmentTab.NEXT_WEEK -> createAssignment(DateUtil.getNextWeekMonday())
                    AssignmentTab.HISTORY -> {}
                }
            }

            is AssignmentContract.Event.OnConfirmButtonClick -> {
                confirmAssignment(false)
            }

            AssignmentContract.Event.OnDismissPopup -> {
                setState { copy(isShowConfirmPopup = false, isShowRegeneratePopup = false) }
            }

            AssignmentContract.Event.OnConfirmClick -> {
                confirmAssignment(true)
            }

            is AssignmentContract.Event.OnWeekSelected -> {
                setState { copy(weekOffset = event.weekOffset) }
                getAssignmentData(DateUtil.getMondayOfWeek(-event.weekOffset))
            }

            AssignmentContract.Event.OnRegenerateClick -> {
                regenerateAssignment()
            }
        }
    }

    private fun getAssignmentData(weekDay: String, isInit: Boolean = false) {
        viewModelScope.launch {
            if (!isInit) {
                setState { copy(isLoading = true) }
            } else {
                setState { copy(mainIsLoading = true) }
            }

            val myInfoResult = getMyInfoUseCase()
            val choresResult = getChoresUseCase()
            val assignmentsResult = getAssignmentsUseCase(weekDay)

            if (myInfoResult is ApiResult.Success && choresResult is ApiResult.Success) {
                val choresNumber = choresResult.data.size
                val isManager = myInfoResult.data.homeRole == 1
                when (assignmentsResult) {
                    is ApiResult.Success -> {
                        val sortedAssignment = assignmentsResult.data.sortItems()
                        val othersPoints = sortedAssignment.memberPoints.filter {
                            it.name != myInfoResult.data.name
                        }

                        val myPoints = sortedAssignment.memberPoints.filter {
                            it.name == myInfoResult.data.name
                        }

                        val memberPoints = othersPoints + myPoints

                        setState {
                            copy(
                                selectedAssignments = sortedAssignment.items,
                                isManager = isManager,
                                screenType = AssignmentScreenType.ASSIGNMENT,
                                assignment = sortedAssignment,
                                memberPoints = memberPoints,
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        if (assignmentsResult.code == 404) {
                            // 분담안 없음
                            val screenType = if (choresNumber >= 3) {
                                AssignmentScreenType.CREATE_ASSIGNMENT
                            } else {
                                AssignmentScreenType.ADD_CHORE
                            }

                            setState { copy(isManager = isManager, screenType = screenType) }
                        }
                    }

                    ApiResult.NetworkError -> {

                    }
                }
            }
            if (!isInit) {
                setState { copy(isLoading = false) }
            } else {
                setState { copy(mainIsLoading = false) }
            }

        }
    }

    private fun createAssignment(weekDay: String) {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }
            val result = createAssignmentUseCase(weekDay)
            when (result) {
                is ApiResult.Success -> {
                    val sortedAssignment = result.data.sortItems()
                    setState {
                        copy(
                            assignment = sortedAssignment,
                            selectedAssignments = sortedAssignment.items,
                            screenType = AssignmentScreenType.ASSIGNMENT,
                        )
                    }
                }

                else -> {

                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun confirmAssignment(acknowledged: Boolean) {
        viewModelScope.launch {
            val result = confirmAssignmentUseCase(viewState.value.assignment?.id ?: 0, acknowledged)
            when (result) {
                is ApiResult.Success -> {
                    if (acknowledged) {
                        setState { copy(isShowConfirmPopup = false, assignment = result.data.assignment) }
                    } else {
                        if (result.data.needsRegenerate) {
                            setState { copy(isShowRegeneratePopup = true) }
                        } else {
                            setState { copy(isShowConfirmPopup = true) }
                        }
                    }
                }

                else -> {

                }
            }
        }
    }

    private fun regenerateAssignment() {
        viewModelScope.launch {
            val result = regenerateAssignmentUseCase(viewState.value.assignment?.id ?: 0)
            when (result) {
                is ApiResult.Success -> {
                    val sortedAssignment = result.data.sortItems()
                    setState {
                        copy(
                            isShowRegeneratePopup = false,
                            assignment = sortedAssignment,
                            selectedAssignments = sortedAssignment.items
                        )
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun onMemberSelected(selectedName: String) {
        val filteredAssignments = if (selectedName == "전체") {
            viewState.value.assignment?.items // 전체일 경우 원본 리스트 그대로
        } else {
            viewState.value.assignment?.items?.filter { it.assignee?.name == selectedName }
        }

        setState {
            copy(
                selectedMember = selectedName,
                selectedAssignments = filteredAssignments ?: emptyList()
            )
        }
    }

    private fun Assignment.sortItems(): Assignment {
        return this.copy(
            items = this.items.sortedBy { item ->
                when (item.changeType) {
                    "new" -> 0
                    "updated" -> 1
                    else -> 2
                }
            }
        )
    }
}
