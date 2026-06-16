package com.devndev.homen.ui.main.home.starterpack.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.core.domain.model.home.Chore

class StarterPackContract {
    sealed class Event: ViewEvent {
        data object OnBackClick: Event()
        data class OnPackSelected(val packType: StarterPackType): Event()
        data class OnTooltipToggle(val show: Boolean): Event()
        data object OnPreviewClick : Event()
//        data class OnChoreChecked(val chore: Chore): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val selectedPack: StarterPackType? = null,
//        val chores: List<Chore> = emptyList(),
//        val selectedChores: List<Chore> = emptyList(),
        val showTooltip: Boolean = true,
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data class NavToPreview(val staterPackType: Int): Effect()
        data object NavToBack: Effect()
    }
}