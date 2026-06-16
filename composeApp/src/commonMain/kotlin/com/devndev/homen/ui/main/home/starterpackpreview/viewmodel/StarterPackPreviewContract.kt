package com.devndev.homen.ui.main.home.starterpackpreview.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.core.domain.model.home.Chore

class StarterPackPreviewContract {
    sealed class Event: ViewEvent {
        data object OnBackClick: Event()
        data class OnChoreChecked(val chore: Chore): Event()
        data class OnInit(val packType: Int): Event()
        data object OnApplyClick: Event()
        data class OnTooltipToggle(val show: Boolean): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val showTooltip: Boolean = true,
        val chores: List<Chore> = emptyList(),
        val selectedChores: List<Chore> = emptyList(),
        val selectedPack: StarterPackType = StarterPackType.ROOMMATE,
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavToBack: Effect()
        data object NavToCreateChore: Effect()
    }
}