package com.devndev.homen.ui.main.mypage.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.auth.ClearTokenUseCase
import com.devndev.homen.core.domain.usecase.auth.LogoutUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.ShareManager
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getHomeUseCase: GetHomeUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
    private val shareManager: ShareManager
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

            MyPageContract.Event.OnPushToggle -> {
                setState {
                    copy(
                        isPushEnabled = !isPushEnabled,
                        isPushDetailExpanded = !isPushEnabled
                    )
                }
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

            is MyPageContract.Event.OnLogoutClick -> {
                if (event.isPopupButton) {
                    logout()
                } else {
                    setState {
                        copy(isShowLogoutPopup = true)
                    }
                }
            }

            MyPageContract.Event.OnShareClick -> {
                shareManager.shareText(viewState.value.inviteCode, viewState.value.homeName)
            }

            MyPageContract.Event.OnDismissPopup -> {
                setState {
                    copy(isShowLogoutPopup = false)
                }
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
            clearTokenUseCase()
            setEffect { MyPageContract.Effect.NavigateToSplash }
        }
    }
}
