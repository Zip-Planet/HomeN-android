package com.devndev.homen.ui.main.assignment.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.common.util.Logger
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoresUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class AssignmentViewModel(
    private val getChoresUseCase: GetChoresUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssignmentsUseCase: GetAssignmentsUseCase
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
                        setState{ copy(isManager = isManager, screenType = AssignmentScreenType.ASSIGNMENT)}
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
}