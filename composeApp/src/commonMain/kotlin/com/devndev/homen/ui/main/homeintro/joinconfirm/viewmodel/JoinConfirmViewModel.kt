package com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetJoinHomeUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class JoinConfirmViewModel(
    private val getJoinHomeUseCase: GetJoinHomeUseCase
) : BaseViewModel<JoinConfirmContract.Event, JoinConfirmContract.State, JoinConfirmContract.Effect>() {

    override fun setInitialState() = JoinConfirmContract.State()

    override fun handleEvents(event: JoinConfirmContract.Event) {
        when (event) {
            is JoinConfirmContract.Event.OnInit -> {
                getJoinHome(event.code)
            }
            JoinConfirmContract.Event.OnJoinClick -> {
                // TODO: 실제 집 참여 API 호출 로직 추가 예정
                setEffect { JoinConfirmContract.Effect.NavigateToDone }
            }
            JoinConfirmContract.Event.OnBackClick -> {
                setEffect { JoinConfirmContract.Effect.PopBackStack }
            }
        }
    }

    private fun getJoinHome(code: String) {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val result = getJoinHomeUseCase(code)

            when (result) {
                is ApiResult.Success -> {
                    setState {
                        copy(
                            homeName = result.data.name,
                            imageId = result.data.imageId,
                            members = result.data.members,
                            createdAt = DateUtil.formatIsoDate(result.data.createdAt)
                        )
                    }
                }
                is ApiResult.Error -> {

                }
                ApiResult.NetworkError -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }
}
