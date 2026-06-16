package com.devndev.homen.ui.main.home.choredetail.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetChoreDetailUseCase
import com.devndev.homen.core.domain.usecase.home.GetMemosUseCase
import kotlinx.coroutines.launch

class ChoreDetailViewModel(
    private val getChoreDetailUseCase: GetChoreDetailUseCase,
    private val getMemosUseCase: GetMemosUseCase
) : BaseViewModel<ChoreDetailContract.Event, ChoreDetailContract.State, ChoreDetailContract.Effect>() {
    override fun setInitialState() = ChoreDetailContract.State()

    override fun handleEvents(event: ChoreDetailContract.Event) {
        when (event) {
            is ChoreDetailContract.Event.OnInit -> {
                getChoreDetail(event.id)
            }

            ChoreDetailContract.Event.OnBackClick -> {
                setEffect { ChoreDetailContract.Effect.NavigateToBack }
            }

            is ChoreDetailContract.Event.OnNavToMemo -> {
                setEffect {
                    ChoreDetailContract.Effect.NavigateToMemo(
                        event.memoId,
                        event.content,
                        event.isEdit
                    )
                }
            }
        }
    }

    private fun getChoreDetail(id: Int) {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val result = getChoreDetailUseCase(id)

            when (result) {
                is ApiResult.Success -> {
                    setState {
                        copy(
                            chore = result.data.chore,
                            weeklyProgress = result.data.weeklyProgress
                        )
                    }
                    getMemos(id)
                }

                is ApiResult.Error -> {

                }

                is ApiResult.NetworkError -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun getMemos(id: Int) {
        viewModelScope.launch {
            val result = getMemosUseCase(id)
            when (result) {
                is ApiResult.Success -> {
                    setState { copy(memos = result.data) }
                }

                is ApiResult.Error -> {

                }

                is ApiResult.NetworkError -> {

                }
            }
        }
    }

}