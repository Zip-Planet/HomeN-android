package com.devndev.homen.ui.main.mypage.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.auth.ClearTokenUseCase
import com.devndev.homen.core.domain.usecase.auth.LogoutUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.notification.GetNotificationSettingUseCase
import com.devndev.homen.core.domain.usecase.notification.UpdateNotificationSettingUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.util.ShareManager
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getHomeUseCase: GetHomeUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
    private val shareManager: ShareManager,
    private val getNotificationSettingUseCase: GetNotificationSettingUseCase,
    private val updateNotificationSettingUseCase: UpdateNotificationSettingUseCase
) : BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {

    override fun setInitialState() = MyPageContract.State()

    override fun handleEvents(event: MyPageContract.Event) {
        when (event) {
            MyPageContract.Event.OnInit -> {
                fetchData()
            }

            MyPageContract.Event.OnProfileSettingClick -> {
                val nickname = viewState.value.nickname
                val avatarId = viewState.value.avatarId ?: 1
                setEffect { MyPageContract.Effect.NavigateToProfileSetting(nickname, avatarId) }
            }

            MyPageContract.Event.OnHomeSettingClick -> {
                setEffect { MyPageContract.Effect.NavigateToHomeSetting }
            }

            MyPageContract.Event.OnPushToggle -> {
                val newPushEnabled = !viewState.value.isPushEnabled
                setState {
                    copy(
                        isPushEnabled = newPushEnabled,
                        isPushDetailExpanded = newPushEnabled
                    )
                }
                viewModelScope.launch {
                    val result = updateNotificationSettingUseCase(pushEnabled = newPushEnabled)
                    if (result is ApiResult.Success) {
                        val data = result.data
                        setState {
                            copy(
                                isPushEnabled = data.pushEnabled,
                                isPushDetailExpanded = data.pushEnabled,
                                isHomeAlarmEnabled = data.homeMember,
                                isAssignmentAlarmEnabled = data.assignment,
                                isBoardAlarmEnabled = data.board,
                                isRewardAlarmEnabled = data.reward,
                                isReportAlarmEnabled = data.report
                            )
                        }
                    }
                }
            }

            is MyPageContract.Event.OnSubPushToggle -> {
                val current = viewState.value
                var homeMember: Boolean? = null
                var assignment: Boolean? = null
                var board: Boolean? = null
                var reward: Boolean? = null
                var report: Boolean? = null

                when (event.type) {
                    MyPageContract.PushType.HOME -> {
                        homeMember = !current.isHomeAlarmEnabled
                        setState { copy(isHomeAlarmEnabled = homeMember) }
                    }
                    MyPageContract.PushType.ASSIGNMENT -> {
                        assignment = !current.isAssignmentAlarmEnabled
                        setState { copy(isAssignmentAlarmEnabled = assignment) }
                    }
                    MyPageContract.PushType.BOARD -> {
                        board = !current.isBoardAlarmEnabled
                        setState { copy(isBoardAlarmEnabled = board) }
                    }
                    MyPageContract.PushType.REWARD -> {
                        reward = !current.isRewardAlarmEnabled
                        setState { copy(isRewardAlarmEnabled = reward) }
                    }
                    MyPageContract.PushType.REPORT -> {
                        report = !current.isReportAlarmEnabled
                        setState { copy(isReportAlarmEnabled = report) }
                    }
                }

                viewModelScope.launch {
                    val result = updateNotificationSettingUseCase(
                        homeMember = homeMember,
                        assignment = assignment,
                        board = board,
                        reward = reward,
                        report = report
                    )
                    if (result is ApiResult.Success) {
                        val data = result.data
                        setState {
                            copy(
                                isPushEnabled = data.pushEnabled,
                                isPushDetailExpanded = data.pushEnabled,
                                isHomeAlarmEnabled = data.homeMember,
                                isAssignmentAlarmEnabled = data.assignment,
                                isBoardAlarmEnabled = data.board,
                                isRewardAlarmEnabled = data.reward,
                                isReportAlarmEnabled = data.report
                            )
                        }
                    }
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
            val notificationResult = getNotificationSettingUseCase()

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

            if (notificationResult is ApiResult.Success) {
                val data = notificationResult.data
                setState {
                    copy(
                        isPushEnabled = data.pushEnabled,
                        isPushDetailExpanded = data.pushEnabled,
                        isHomeAlarmEnabled = data.homeMember,
                        isAssignmentAlarmEnabled = data.assignment,
                        isBoardAlarmEnabled = data.board,
                        isRewardAlarmEnabled = data.reward,
                        isReportAlarmEnabled = data.report
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
