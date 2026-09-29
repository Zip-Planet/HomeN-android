package com.devndev.homen.ui.main.mypage.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.DeleteHomeUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.home.LeaveHomeUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import kotlinx.coroutines.launch

class HomeSettingViewModel(
    private val getHomeUseCase: GetHomeUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val leaveHomeUseCase: LeaveHomeUseCase,
    private val deleteHomeUseCase: DeleteHomeUseCase
) : BaseViewModel<HomeSettingContract.Event, HomeSettingContract.State, HomeSettingContract.Effect>() {

    override fun setInitialState() = HomeSettingContract.State()

    override fun handleEvents(event: HomeSettingContract.Event) {
        when (event) {
            HomeSettingContract.Event.OnInit -> {
                fetchHomeData()
            }

            HomeSettingContract.Event.OnLeaveClick -> {
                val state = viewState.value
                val totalMembers = state.members.size

                val popupType = when {
                    totalMembers <= 1 -> HomeSettingContract.SettingPopupType.DISBAND
                    state.isManager -> HomeSettingContract.SettingPopupType.CANNOT_LEAVE
                    else -> HomeSettingContract.SettingPopupType.LEAVE
                }

                setState { copy(activePopup = popupType) }
            }

            HomeSettingContract.Event.OnDelegateManagerClick -> {
                setEffect { HomeSettingContract.Effect.NavigateToDelegateManager }
            }

            HomeSettingContract.Event.OnConfirmLeave -> {
                leaveHome()
            }

            HomeSettingContract.Event.OnConfirmDisband -> {
                deleteHome()
            }

            HomeSettingContract.Event.OnDismissPopup -> {
                setState { copy(activePopup = HomeSettingContract.SettingPopupType.NONE) }
            }

            HomeSettingContract.Event.OnBackClick -> {
                setEffect { HomeSettingContract.Effect.PopBackStack }
            }
        }
    }

    private fun fetchHomeData() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val myInfoResult = getMyInfoUseCase()
            var myName = ""
            var myRole = 2

            if (myInfoResult is ApiResult.Success) {
                myName = myInfoResult.data.name
                myRole = myInfoResult.data.homeRole ?: 2
            }

            val homeResult = getHomeUseCase()
            if (homeResult is ApiResult.Success) {
                val isManager = myRole == 1
                setState {
                    copy(
                        homeName = homeResult.data.name,
                        homeImageId = homeResult.data.image,
                        myName = myName,
                        isManager = isManager,
                        members = homeResult.data.members
                    )
                }
            }

            setState { copy(isLoading = false) }
        }
    }

    private fun leaveHome() {
        setState { copy(activePopup = HomeSettingContract.SettingPopupType.NONE) }
        viewModelScope.launch {
            when (leaveHomeUseCase()) {
                is ApiResult.Success -> {
                    setEffect { HomeSettingContract.Effect.NavigateToHomeIntro }
                }
                is ApiResult.Error, ApiResult.NetworkError -> {
                    // Handle error if needed
                }
            }
        }
    }

    private fun deleteHome() {
        setState { copy(activePopup = HomeSettingContract.SettingPopupType.NONE) }
        viewModelScope.launch {
            when(deleteHomeUseCase()) {
                is ApiResult.Success -> {
                    setEffect { HomeSettingContract.Effect.NavigateToHomeIntro }
                }
                is ApiResult.Error, ApiResult.NetworkError -> {
                    // Handle error if needed
                }
            }
        }
    }
}
