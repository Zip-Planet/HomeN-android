package com.devndev.homen.ui.main.home.memo.viewModel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.CreateMemoUseCase
import kotlinx.coroutines.launch

class MemoViewModel(
    private val createMemoUseCase: CreateMemoUseCase,
): BaseViewModel<MemoContract.Event, MemoContract.State, MemoContract.Effect>() {
    override fun setInitialState() = MemoContract.State()

    override fun handleEvents(event: MemoContract.Event) {
        when (event) {
            is MemoContract.Event.OnSaveClick -> {
                when (event.isEdit) {
                    true -> {

                    }
                    false -> {
                        createMemo(choreId = event.choreId)
                    }
                }
            }

            is MemoContract.Event.OnInitEdit -> {
                setState { copy(content = event.content) }
            }

            is MemoContract.Event.OnValueChange -> {
                setState { copy(content = event.content) }
            }
            MemoContract.Event.OnBackClick -> {
                setEffect { MemoContract.Effect.NavigateToBack }
            }
        }
    }

    private fun createMemo(choreId: Int) {
        viewModelScope.launch {
            val result = createMemoUseCase(id = choreId, content = viewState.value.content)
            when (result) {
                is ApiResult.Success -> {
                    setEffect { MemoContract.Effect.NavigateToBack }
                }
                is ApiResult.Error -> {

                }
                ApiResult.NetworkError -> {

                }
            }
        }
    }
}