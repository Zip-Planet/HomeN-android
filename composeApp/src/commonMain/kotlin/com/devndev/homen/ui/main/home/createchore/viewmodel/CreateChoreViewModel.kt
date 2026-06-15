package com.devndev.homen.ui.main.home.createchore.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.usecase.home.CreateChoreUseCase
import com.devndev.homen.core.domain.usecase.home.EditChoreUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoreDetailUseCase
import kotlinx.coroutines.launch

class CreateChoreViewModel(
    private val createChoreUseCase: CreateChoreUseCase,
    private val getChoreDetailUseCase: GetChoreDetailUseCase,
    private val editChoreUseCase: EditChoreUseCase
) : BaseViewModel<CreateChoreContract.Event, CreateChoreContract.State, CreateChoreContract.Effect>() {
    override fun setInitialState() = CreateChoreContract.State()

    override fun handleEvents(event: CreateChoreContract.Event) {
        when (event) {
            CreateChoreContract.Event.OnBackClick -> {
                setEffect { CreateChoreContract.Effect.NavigateToBack }
            }

            is CreateChoreContract.Event.OnCategoryClick -> {
                setState { copy(selectedCategory = event.choreCategory) }
            }

            is CreateChoreContract.Event.OnDescriptionChange -> {
                setState { copy(description = event.description) }
            }

            is CreateChoreContract.Event.OnTitleChange -> {
                setState { copy(title = event.title) }
            }

            is CreateChoreContract.Event.OnDayClick -> {
                val currentSelected = viewState.value.selectedDays
                val selected = if (currentSelected.contains(event.day.value)) {
                    currentSelected - event.day.value
                } else {
                    currentSelected + event.day.value
                }
                setState { copy(selectedDays = selected) }
            }

            is CreateChoreContract.Event.OnSaveClick -> {
                if (event.isEdit) {
                    editChore(event.id!!)
                } else {
                    createChore()
                }
            }

            is CreateChoreContract.Event.OnDifficultyClick -> {
                setState { copy(selectedDifficulty = event.difficulty) }
            }

            is CreateChoreContract.Event.OnEdit -> {
                getChoreDetail(event.id)
            }
        }
    }

    private fun createChore() {
        viewModelScope.launch {
            val result = createChoreUseCase(
                chores = listOf(
                    Chore(
                        category = viewState.value.selectedCategory?.id ?: 1,
                        name = viewState.value.title,
                        description = viewState.value.description,
                        repeatDays = viewState.value.selectedDays.toList(),
                        difficulty = viewState.value.selectedDifficulty ?: ChoreDifficulty.LOW
                    )
                )
            )
            when (result) {
                is ApiResult.Success -> {
                    setEffect { CreateChoreContract.Effect.NavigateToBack }
                }

                is ApiResult.Error -> {

                }

                is ApiResult.NetworkError -> {

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
                            title = result.data.name,
                            description = result.data.description,
                            selectedCategory = ChoreCategory.fromId(result.data.category),
                            selectedDays = result.data.repeatDays.toSet(),
                            selectedDifficulty = result.data.difficulty
                        )
                    }
                }

                is ApiResult.Error -> {

                }

                is ApiResult.NetworkError -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun editChore(id: Int) {
        viewModelScope.launch {
            val result = editChoreUseCase(
                Chore(
                    id = id,
                    category = viewState.value.selectedCategory?.id ?: 1,
                    name = viewState.value.title,
                    description = viewState.value.description,
                    repeatDays = viewState.value.selectedDays.toList(),
                    difficulty = viewState.value.selectedDifficulty ?: ChoreDifficulty.LOW
                )
            )
            when (result) {
                is ApiResult.Success -> {
                    setEffect { CreateChoreContract.Effect.NavigateToBack }
                }

                is ApiResult.Error -> {

                }

                is ApiResult.NetworkError -> {

                }
            }
        }

    }
}


