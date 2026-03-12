package com.devndev.homen.ui.main.homeintro.create.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class CreateHomeContract {
    sealed class Event : ViewEvent {
        data class OnHomeNameChanged(val name: String) : Event()

        data class OnPackSelected(val packId: Int) : Event()

        data class OnRewardChanged(val reward: String) : Event()

        data object OnNextClick : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val homeName: String = "",
        val selectedPackId: Int? = null,
        val rewards: String = "",
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
//        data object NavigateToProfile : Effect()
//        data object NavigateToPack : Effect()
//        data object NavigateToReward : Effect()
//        data object NavigateToDone : Effect()
        data object NavToNext : Effect()
        data object PopBackStack : Effect()
    }
}
