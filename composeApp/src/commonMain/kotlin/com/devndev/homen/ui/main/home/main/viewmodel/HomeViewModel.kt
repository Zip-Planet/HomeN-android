package com.devndev.homen.ui.main.home.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Member
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentStatus
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeUseCase: GetHomeUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentsUseCase: GetAssignmentsUseCase
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    override fun setInitialState() = HomeContract.State()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.OnInit -> {
                getHomeData()
            }

            is HomeContract.Event.OnMemberSelected -> {
                onMemberSelected(event.member)
            }

            HomeContract.Event.OnChoreManageClick -> {
                setEffect { HomeContract.Effect.NavigateToChoreManage }
            }

            HomeContract.Event.OnCreateAssignmentClick -> {
                setEffect { HomeContract.Effect.NavigateToAssignment(true) }
            }

            HomeContract.Event.OnAssignmentClick -> {
                setEffect { HomeContract.Effect.NavigateToAssignment(false) }
            }
        }
    }

    private fun getHomeData() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }

            val homeResult = getHomeUseCase()
            val myInfoResult = getMyInfoUseCase()

            if (homeResult is ApiResult.Success && myInfoResult is ApiResult.Success) {
                val homeData = homeResult.data
                val myName = myInfoResult.data.name
                val otherMembers = homeData.members
                    .filter { it.name != myName }

                val myInfo = homeData.members.find { it.name == myName }

                val memberList = listOfNotNull(myInfo) + otherMembers
                setState {
                    copy(
                        homeIcon = homeData.image,
                        homeName = homeData.name,
                        totalMember = homeData.members.size,
                        members = memberList,
                        selectedMember = myInfo,
                    )
                }
                getThisWeekAssignments(memberList)
                getNextWeekAssignmentStatus()
            }
        }
    }

    private fun getNextWeekAssignmentStatus() {
        viewModelScope.launch {
            val result = getAssignmentsUseCase(DateUtil.getNextWeekMonday())

            when (result) {
                is ApiResult.Success -> {
                    setState { copy(assignmentStatus = result.data.status) }
                }

                else -> {
                    setState { copy(assignmentStatus = "none") }
                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun getThisWeekAssignments(members: List<Member>) {
        viewModelScope.launch {
            val result = getAssignmentsUseCase(DateUtil.getThisWeekMonday())

            when (result) {
                is ApiResult.Success -> {
                    if (result.data.status == AssignmentStatus.CONFIRMED.status) {
                        val items = result.data.items
                        val totalChore = items.size
                        val completedChore = items.count { it.isCompleted }

                        val pointsMap = members.associate { it.name to 0 }.toMutableMap()
                        items.filter { it.isCompleted && it.assignee != null }.forEach {
                            val name = it.assignee!!.name
                            pointsMap[name] = (pointsMap[name] ?: 0) + it.point
                        }

                        val mvp = pointsMap.toList()
                            .sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })
                            .firstOrNull()

                        setState {
                            copy(
                                assignment = result.data,
                                choreExist = true,
                                totalChore = totalChore,
                                completedChore = completedChore,
                                mvpName = mvp?.first ?: "",
                                mvpPoint = mvp?.second ?: 0
                            )
                        }
                        onMemberSelected(viewState.value.selectedMember)
                    } else {
                        setState { copy(choreExist = false) }
                    }
                }

                else -> {
                    setState { copy(choreExist = false) }
                }
            }
        }
    }

    private fun onMemberSelected(selectedMember: Member?) {
        val filteredAssignments = viewState.value.assignment?.items?.filter { it.assignee?.name == selectedMember?.name }

        setState { copy(selectedMember = selectedMember, selectedAssignments = filteredAssignments ?: emptyList()) }
    }
}
