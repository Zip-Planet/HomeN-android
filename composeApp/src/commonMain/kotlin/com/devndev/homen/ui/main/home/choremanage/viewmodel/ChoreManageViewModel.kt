package com.devndev.homen.ui.main.home.choremanage.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.DeleteChoreUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoresUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChoreManageViewModel(
    private val getHomeUseCase: GetHomeUseCase,
    private val getChoresUseCase: GetChoresUseCase,
    private val deleteChoreUseCase: DeleteChoreUseCase
): BaseViewModel<ChoreManageContract.Event, ChoreManageContract.State, ChoreManageContract.Effect>() {
    
    private val pendingDeleteJobs = mutableMapOf<Int, Job>()

    override fun setInitialState() = ChoreManageContract.State()

    override fun handleEvents(event: ChoreManageContract.Event) {
        when (event) {
            ChoreManageContract.Event.OnInit -> {
                getChoreManageData()
            }
            ChoreManageContract.Event.OnBackClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToBack }
            }
            
            ChoreManageContract.Event.OnDispose -> {
                flushPendingDeletes()
            }

            is ChoreManageContract.Event.OnTooltipClick -> {
                if (event.isEmptyChore) {
                    setState { copy(isEmptyChoreTooltipShow = event.show) }
                } else {
                    setState { copy(isNotEmptyChoreTooltipShow = event.show) }
                }
            }

            ChoreManageContract.Event.OnNextButtonClick -> {
                when (viewState.value.selectedOption) {
                    1-> setEffect { ChoreManageContract.Effect.NavigateToCrateChore }
                    2 -> setEffect { ChoreManageContract.Effect.NavigateToStaterPack }
                }
            }
            is ChoreManageContract.Event.OnOptionClick -> {
                setState { copy(selectedOption = event.option) }
            }

            ChoreManageContract.Event.OnAddButtonClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToCrateChore }
            }

            is ChoreManageContract.Event.OnEditClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToEditChore(event.id) }
            }

            is ChoreManageContract.Event.OnChoreClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToChoreDetail(event.id) }
            }
            
            is ChoreManageContract.Event.OnDeleteClick -> {
                val index = viewState.value.chores.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    val chore = viewState.value.chores[index]
                    val updatedList = viewState.value.chores.toMutableList().apply {
                        removeAt(index)
                    }
                    setState { copy(chores = updatedList) }
                    startPendingDelete(event.id)
                    setEffect { ChoreManageContract.Effect.ShowDeleteSnackBar(chore, index) }
                }
            }
            
            is ChoreManageContract.Event.OnUndoDelete -> {
                val id = event.chore.id
                if (id != null) {
                    pendingDeleteJobs[id]?.cancel()
                    pendingDeleteJobs.remove(id)
                }

                val currentList = viewState.value.chores.toMutableList()
                if (event.index in 0..currentList.size) {
                    currentList.add(event.index, event.chore)
                } else {
                    currentList.add(event.chore)
                }
                setState { copy(chores = currentList) }
            }

            is ChoreManageContract.Event.OnDeleteConfirm -> {
                pendingDeleteJobs[event.id]?.cancel()
                pendingDeleteJobs.remove(event.id)
                deleteChore(event.id)
            }
        }
    }

    private fun startPendingDelete(id: Int) {
        pendingDeleteJobs[id]?.cancel()
        val job = viewModelScope.launch {
            try {
                delay(5000)
                deleteChore(id)
                pendingDeleteJobs.remove(id)
            } catch (e: Exception) {
                // Cancelled
            }
        }
        pendingDeleteJobs[id] = job
    }

    private fun flushPendingDeletes() {
        if (pendingDeleteJobs.isEmpty()) return
        pendingDeleteJobs.forEach { (id, job) ->
            job.cancel()
            viewModelScope.launch(NonCancellable) {
                deleteChoreUseCase(id)
            }
        }
        pendingDeleteJobs.clear()
    }

    override fun onCleared() {
        flushPendingDeletes()
        super.onCleared()
    }

    private fun getChoreManageData() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val homeResult = getHomeUseCase()
            val choreResult = getChoresUseCase()
            if (homeResult is ApiResult.Success && choreResult is ApiResult.Success) {
                val filteredChores = choreResult.data.filter { it.id !in pendingDeleteJobs.keys }
                setState { copy(homeName = homeResult.data.name, chores = filteredChores) }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun deleteChore(id: Int) {
        viewModelScope.launch(NonCancellable) {
            val result = deleteChoreUseCase(id)
            if (result !is ApiResult.Success) {
                getChoreManageData()
            }
        }
    }
}