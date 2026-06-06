package com.devndev.homen.ui.main.homeintro.join.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetJoinHomeUseCase
import kotlinx.coroutines.launch

class CodeEnterViewModel(
    private val getJoinHomeUseCase: GetJoinHomeUseCase
) : BaseViewModel<CodeEnterContract.Event, CodeEnterContract.State, CodeEnterContract.Effect>() {

    override fun setInitialState() = CodeEnterContract.State()

    override fun handleEvents(event: CodeEnterContract.Event) {
        when (event) {
            is CodeEnterContract.Event.OnCodeChanged -> {
                setState { copy(code = event.code, codeEnterStep = CodeEnterStep.NONE) }
            }
            CodeEnterContract.Event.OnJoinClick -> {
                joinHome()
            }
            CodeEnterContract.Event.OnBackClick -> {
                setEffect { CodeEnterContract.Effect.PopBackStack }
            }

            is CodeEnterContract.Event.OnTooltipToggle -> {
                setState { copy(showTooltip = event.show) }
            }
        }
    }

    private fun joinHome() {
        viewModelScope.launch {
            val result = getJoinHomeUseCase(viewState.value.code)

            when (result) {
                is ApiResult.Success -> {
                    setEffect { CodeEnterContract.Effect.NavigateToConfirm }
                }
                is ApiResult.Error -> {
                    if (result.code == 404) {
                        setState { copy(codeEnterStep = CodeEnterStep.INVALID) }
                    }
                }
                ApiResult.NetworkError -> {

                }
            }
        }
    }
}
