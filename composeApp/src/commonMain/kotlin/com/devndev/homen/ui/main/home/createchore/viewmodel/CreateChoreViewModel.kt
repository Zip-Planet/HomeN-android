package com.devndev.homen.ui.main.home.createchore.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class CreateChoreViewModel(

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

            CreateChoreContract.Event.OnSaveClick -> {

            }

            is CreateChoreContract.Event.OnDifficultyClick -> {
                setState { copy(selectedDifficulty = event.difficulty) }
            }
        }
    }
}


