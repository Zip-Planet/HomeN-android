package com.devndev.homen.ui.main.home.choremanage.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.DeleteChoreUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoresUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import kotlinx.coroutines.launch

class ChoreManageViewModel(
    private val getHomeUseCase: GetHomeUseCase,
    private val getChoresUseCase: GetChoresUseCase,
    private val deleteChoreUseCase: DeleteChoreUseCase
): BaseViewModel<ChoreManageContract.Event, ChoreManageContract.State, ChoreManageContract.Effect>() {
    override fun setInitialState() = ChoreManageContract.State()

    override fun handleEvents(event: ChoreManageContract.Event) {
        when (event) {
            ChoreManageContract.Event.OnInit -> {
                getChoreManageData()
            }
            ChoreManageContract.Event.OnBackClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToBack }
            }

            is ChoreManageContract.Event.OnTooltipClick -> {
                if (event.isEmptyChore) {
                    setState { copy(isEmptyChoreTooltipShow = event.show) }
                } else {
                    setState { copy(isNotEmptyChoreTooltipShow = event.show) }
                }
            }

            ChoreManageContract.Event.OnNextButtonClick -> {
                // TODO option 화면 전환
                when (viewState.value.selectedOption) {
                    1-> {
                        setEffect { ChoreManageContract.Effect.NavigateToCrateChore }
                    }
                    2 -> {

                    }
                }
            }
            is ChoreManageContract.Event.OnOptionClick -> {
                setState { copy(selectedOption = event.option) }
            }

            ChoreManageContract.Event.OnAddButtonClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToCrateChore }
            }

            is ChoreManageContract.Event.OnDeleteClick -> {
                deleteChore(event.id)
            }

            is ChoreManageContract.Event.OnEditClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToEditChore(event.id) }
            }

            is ChoreManageContract.Event.OnChoreClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToChoreDetail(event.id) }
            }
        }
    }

    private fun getChoreManageData() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val homeResult = getHomeUseCase()
            val choreResult = getChoresUseCase()
            if (homeResult is ApiResult.Success && choreResult is ApiResult.Success) {
                setState { copy(homeName = homeResult.data.name, chores = choreResult.data) }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun deleteChore(id: Int) {
        viewModelScope.launch {
            val result = deleteChoreUseCase(id)
            when (result) {
                is ApiResult.Success -> {
                    getChoreManageData()
                }
                is ApiResult.Error -> {

                }
                ApiResult.NetworkError -> {

                }
            }
        }
    }
}