package com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetJoinHomeUseCase
import com.devndev.homen.core.domain.usecase.home.JoinHomeUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class JoinConfirmViewModel(
    private val getJoinHomeUseCase: GetJoinHomeUseCase,
    private val joinHomeUseCase: JoinHomeUseCase
) : BaseViewModel<JoinConfirmContract.Event, JoinConfirmContract.State, JoinConfirmContract.Effect>() {

    override fun setInitialState() = JoinConfirmContract.State()

    override fun handleEvents(event: JoinConfirmContract.Event) {
        when (event) {
            is JoinConfirmContract.Event.OnInit -> {
                getJoinHome(event.code)
            }
            is JoinConfirmContract.Event.OnJoinClick -> {
                joinHome(event.code)
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

    private fun joinHome(code: String) {
        viewModelScope.launch {
            val result = joinHomeUseCase(code)

            when (result) {
                is ApiResult.Success -> {
                    setEffect { JoinConfirmContract.Effect.NavigateToDone }
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
