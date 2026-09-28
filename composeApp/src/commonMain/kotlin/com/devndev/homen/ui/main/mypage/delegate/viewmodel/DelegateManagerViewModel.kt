package com.devndev.homen.ui.main.mypage.delegate.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.home.TransferAdminUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import kotlinx.coroutines.launch

class DelegateManagerViewModel(
    private val getHomeUseCase: GetHomeUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val transferAdminUseCase: TransferAdminUseCase
) : BaseViewModel<DelegateManagerContract.Event, DelegateManagerContract.State, DelegateManagerContract.Effect>() {

    override fun setInitialState() = DelegateManagerContract.State()

    override fun handleEvents(event: DelegateManagerContract.Event) {
        when (event) {
            DelegateManagerContract.Event.OnInit -> {
                fetchData()
            }

            is DelegateManagerContract.Event.OnMemberSelect -> {
                val currentSelected = viewState.value.selectedMember
                val newSelected = if (currentSelected == event.member) null else event.member
                setState {
                    copy(
                        selectedMember = newSelected,
                        isDelegateEnabled = newSelected != null
                    )
                }
            }

            DelegateManagerContract.Event.OnDelegateClick -> {
                if (viewState.value.selectedMember != null) {
                    setState { copy(isShowPopup = true) }
                }
            }

            DelegateManagerContract.Event.OnConfirmDelegate -> {
                delegateManager()
            }

            DelegateManagerContract.Event.OnDismissPopup -> {
                setState { copy(isShowPopup = false) }
            }

            DelegateManagerContract.Event.OnBackClick -> {
                setEffect { DelegateManagerContract.Effect.PopBackStack }
            }
        }
    }

    private fun fetchData() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val myInfoResult = getMyInfoUseCase()
            var myName = ""
            var myProfileImage: Int? = null

            if (myInfoResult is ApiResult.Success) {
                myName = myInfoResult.data.name
                myProfileImage = myInfoResult.data.profileImage
            }

            val homeResult = getHomeUseCase()
            if (homeResult is ApiResult.Success) {
                val candidates = homeResult.data.members.filter { it.name != myName }
                val currentManager = homeResult.data.members.find { it.role == 1 }?.name ?: myName

                setState {
                    copy(
                        currentManagerName = currentManager,
                        myProfileImage = myProfileImage,
                        candidates = candidates
                    )
                }
            }

            setState { copy(isLoading = false) }
        }
    }

    private fun delegateManager() {
        val selectedMember = viewState.value.selectedMember ?: return
        setState { copy(isShowPopup = false, isLoading = true) }
        viewModelScope.launch {
            when (transferAdminUseCase(selectedMember.userId)) {
                is ApiResult.Success -> {
                    setEffect { DelegateManagerContract.Effect.DelegateSuccess }
                }
                is ApiResult.Error, ApiResult.NetworkError -> {
                    // Handle error if needed
                }
            }
            setState { copy(isLoading = false) }
        }
    }
}
