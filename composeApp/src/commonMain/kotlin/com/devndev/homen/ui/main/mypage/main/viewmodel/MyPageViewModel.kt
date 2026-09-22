package com.devndev.homen.ui.main.mypage.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.auth.LogoutUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getHomeUseCase: GetHomeUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {

    override fun setInitialState() = MyPageContract.State()

    override fun handleEvents(event: MyPageContract.Event) {
        when (event) {
            MyPageContract.Event.OnInit -> {
                fetchData()
            }
            MyPageContract.Event.OnProfileSettingClick -> {
                setEffect { MyPageContract.Effect.NavigateToProfileSetting }
            }
            MyPageContract.Event.OnHomeSettingClick -> {
                setEffect { MyPageContract.Effect.NavigateToHomeSetting }
            }
            MyPageContract.Event.OnCopyInviteCode -> {
                setEffect { MyPageContract.Effect.ShowToast("초대 코드를 복사했습니다.") }
            }
            MyPageContract.Event.OnShareInviteCode -> {
                setEffect { MyPageContract.Effect.ShareInviteCode(viewState.value.inviteCode) }
            }
            MyPageContract.Event.OnPushToggle -> {
                setState { copy(isPushEnabled = !isPushEnabled, isPushDetailExpanded = !isPushEnabled) }
            }
            is MyPageContract.Event.OnSubPushToggle -> {
                when (event.type) {
                    MyPageContract.PushType.HOME -> setState { copy(isHomeAlarmEnabled = !isHomeAlarmEnabled) }
                    MyPageContract.PushType.ASSIGNMENT -> setState { copy(isAssignmentAlarmEnabled = !isAssignmentAlarmEnabled) }
                    MyPageContract.PushType.BOARD -> setState { copy(isBoardAlarmEnabled = !isBoardAlarmEnabled) }
                    MyPageContract.PushType.REWARD -> setState { copy(isRewardAlarmEnabled = !isRewardAlarmEnabled) }
                    MyPageContract.PushType.REPORT -> setState { copy(isReportAlarmEnabled = !isReportAlarmEnabled) }
                }
            }
            MyPageContract.Event.OnSupportClick -> {
                setEffect { MyPageContract.Effect.NavigateToSupport }
            }
            MyPageContract.Event.OnLogoutClick -> {
                logout()
            }
        }
    }

    private fun fetchData() {
        setState { copy(mainIsLoading = true) }
        viewModelScope.launch {
            val myInfoResult = getMyInfoUseCase()
            val homeResult = getHomeUseCase()

            if (myInfoResult is ApiResult.Success) {
                setState {
                    copy(
                        nickname = myInfoResult.data.name,
                        avatarId = myInfoResult.data.profileImage,
                        homeRole = myInfoResult.data.homeRole
                    )
                }
            }

            if (homeResult is ApiResult.Success) {
                setState {
                    copy(
                        homeName = homeResult.data.name,
                        inviteCode = homeResult.data.inviteCode
                    )
                }
            }
            setState { copy(mainIsLoading = false) }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = logoutUseCase()
            if (result is ApiResult.Success) {
                setEffect { MyPageContract.Effect.NavigateToLogin }
            } else {
                // Handle error
            }
            setState { copy(isLoading = false) }
        }
    }
}
