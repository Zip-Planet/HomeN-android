package com.devndev.homen.ui.main.home.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
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
                getNextWeekAssignmentStatus()
            }

            is HomeContract.Event.OnMemberSelected -> {
                setState { copy(selectedMember = event.member) }
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
                        mainIsLoading = false,
                        homeIcon = homeData.image,
                        homeName = homeData.name,
                        totalMember = homeData.members.size,
                        members = memberList,
                        selectedMember = myInfo
                    )
                }
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
        }
    }
}