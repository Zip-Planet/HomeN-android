package com.devndev.homen.ui.main.home.choredetail.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.DeleteMemoUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoreDetailUseCase
import com.devndev.homen.core.domain.usecase.home.GetMemosUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChoreDetailViewModel(
    private val getChoreDetailUseCase: GetChoreDetailUseCase,
    private val getMemosUseCase: GetMemosUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase
) : BaseViewModel<ChoreDetailContract.Event, ChoreDetailContract.State, ChoreDetailContract.Effect>() {
    
    private val pendingDeleteJobs = mutableMapOf<Int, Job>()
    
    override fun setInitialState() = ChoreDetailContract.State()

    override fun handleEvents(event: ChoreDetailContract.Event) {
        when (event) {
            is ChoreDetailContract.Event.OnInit -> {
                getChoreDetail(event.id)
            }

            ChoreDetailContract.Event.OnBackClick -> {
                setEffect { ChoreDetailContract.Effect.NavigateToBack }
            }
            
            ChoreDetailContract.Event.OnDispose -> {
                flushPendingDeletes()
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

            is ChoreDetailContract.Event.OnDeleteMemo -> {
                val index = viewState.value.memos.indexOfFirst { it.id == event.memoId }
                if (index != -1) {
                    val memo = viewState.value.memos[index]
                    val updatedList = viewState.value.memos.toMutableList().apply {
                        removeAt(index)
                    }
                    setState { copy(memos = updatedList) }
                    startPendingDelete(event.memoId)
                    setEffect { ChoreDetailContract.Effect.ShowDeleteMemoSnackBar(memo, index) }
                }
            }
            
            is ChoreDetailContract.Event.OnUndoDeleteMemo -> {
                pendingDeleteJobs[event.memo.id]?.cancel()
                pendingDeleteJobs.remove(event.memo.id)
                
                val currentList = viewState.value.memos.toMutableList()
                if (event.index in 0..currentList.size) {
                    currentList.add(event.index, event.memo)
                } else {
                    currentList.add(event.memo)
                }
                setState { copy(memos = currentList) }
            }
            
            is ChoreDetailContract.Event.OnDeleteConfirmMemo -> {
                pendingDeleteJobs[event.memoId]?.cancel()
                pendingDeleteJobs.remove(event.memoId)
                deleteMemo(viewState.value.chore.id!!, event.memoId)
            }

            is ChoreDetailContract.Event.OnDeleteChore -> {
                val choreId = viewState.value.chore.id
                if (choreId != null) {
                    setEffect { ChoreDetailContract.Effect.NavigateToBackWithDelete(choreId) }
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
                else -> {}
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun getMemos(id: Int) {
        viewModelScope.launch {
            val result = getMemosUseCase(id)
            when (result) {
                is ApiResult.Success -> {
                    val filteredMemos = result.data.filter { it.id !in pendingDeleteJobs.keys }
                    setState { copy(memos = filteredMemos) }
                }
                else -> {}
            }
        }
    }

    private fun startPendingDelete(memoId: Int) {
        pendingDeleteJobs[memoId]?.cancel()
        val job = viewModelScope.launch {
            try {
                delay(5000)
                deleteMemo(viewState.value.chore.id!!, memoId)
                pendingDeleteJobs.remove(memoId)
            } catch (e: Exception) {
                // Cancelled
            }
        }
        pendingDeleteJobs[memoId] = job
    }

    private fun flushPendingDeletes() {
        if (pendingDeleteJobs.isEmpty()) return
        val choreId = viewState.value.chore.id ?: return
        
        pendingDeleteJobs.forEach { (id, job) ->
            job.cancel()
            viewModelScope.launch(NonCancellable) {
                deleteMemoUseCase(choreId, id)
            }
        }
        pendingDeleteJobs.clear()
    }

    override fun onCleared() {
        flushPendingDeletes()
        super.onCleared()
    }

    private fun deleteMemo(choreId: Int, memoId: Int) {
        viewModelScope.launch(NonCancellable) {
            val result = deleteMemoUseCase(choreId, memoId)
            if (result !is ApiResult.Success) {
                getMemos(choreId)
            }
        }
    }
}