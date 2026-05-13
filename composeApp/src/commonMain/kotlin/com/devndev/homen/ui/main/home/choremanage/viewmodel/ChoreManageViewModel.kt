package com.devndev.homen.ui.main.home.choremanage.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class ChoreManageViewModel(

): BaseViewModel<ChoreManageContract.Event, ChoreManageContract.State, ChoreManageContract.Effect>() {
    override fun setInitialState() = ChoreManageContract.State()

    override fun handleEvents(event: ChoreManageContract.Event) {
        when (event) {
            ChoreManageContract.Event.OnBackClick -> {
                setEffect { ChoreManageContract.Effect.NavigateToBack }
            }

            is ChoreManageContract.Event.OnTooltipClick -> {
                setState { copy(isEmptyChoreTooltipShow = event.show) }
            }

            ChoreManageContract.Event.OnNextButtonClick -> {
                // TODO option 화면 전환
                when (viewState.value.selectedOption) {
                    1-> {

                    }
                    2 -> {

                    }
                }
            }
            is ChoreManageContract.Event.OnOptionClick -> {
                setState { copy(selectedOption = event.option) }
            }
        }
    }
}