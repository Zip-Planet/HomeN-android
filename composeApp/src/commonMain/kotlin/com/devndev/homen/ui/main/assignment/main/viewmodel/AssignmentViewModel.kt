package com.devndev.homen.ui.main.assignment.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.CreateAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoresUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class AssignmentViewModel(
    private val getChoresUseCase: GetChoresUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentsUseCase: GetAssignmentsUseCase,
    private val createAssignmentUseCase: CreateAssignmentUseCase
) : BaseViewModel<AssignmentContract.Event, AssignmentContract.State, AssignmentContract.Effect>() {
    override fun setInitialState() = AssignmentContract.State()
    override fun handleEvents(event: AssignmentContract.Event) {
        when (event) {
            AssignmentContract.Event.OnInit -> {
                getAssignmentData()
            }

            is AssignmentContract.Event.OnTabSelected -> {
                setState { copy(selectedTab = event.tab) }
            }

            is AssignmentContract.Event.OnSelectedMember -> {
                onMemberSelected(event.member)
            }

            AssignmentContract.Event.OnAddChoreClick -> {
                setEffect { AssignmentContract.Effect.NavigateToChoreManage }
            }

            AssignmentContract.Event.OnCreateAssignmentClick -> {
                createAssignment()
            }
        }
    }

    private fun getAssignmentData() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }
            val myInfoResult = getMyInfoUseCase()
            val choresResult = getChoresUseCase()
            val assignmentsResult = getAssignmentsUseCase(DateUtil.getThisWeekMonday())

            if (myInfoResult is ApiResult.Success && choresResult is ApiResult.Success) {
                val choresNumber = choresResult.data.size
                val isManager = myInfoResult.data.homeRole == 1
                when (assignmentsResult) {
                    is ApiResult.Success -> {
                        val othersPoints = assignmentsResult.data.memberPoints.filter {
                            it.name != myInfoResult.data.name
                        }

                        val myPoints = assignmentsResult.data.memberPoints.filter {
                            it.name == myInfoResult.data.name
                        }

                        val memberPoints = othersPoints + myPoints

                        setState {
                            copy(
                                selectedAssignments = assignmentsResult.data.items,
                                isManager = isManager,
                                screenType = AssignmentScreenType.ASSIGNMENT,
                                assignment = assignmentsResult.data,
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
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun createAssignment() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }
            val result = createAssignmentUseCase(DateUtil.getThisWeekMonday())
            when (result) {
                is ApiResult.Success -> {
                    setState {
                        copy(
                            assignment = result.data,
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
}