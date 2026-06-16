package com.devndev.homen.ui.main.home.starterpack.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class StarterPackViewModel(

) : BaseViewModel<StarterPackContract.Event, StarterPackContract.State, StarterPackContract.Effect>() {
    override fun setInitialState() = StarterPackContract.State()

    override fun handleEvents(event: StarterPackContract.Event) {
        when (event) {
            is StarterPackContract.Event.OnPackSelected -> {
                setState { copy(selectedPack = event.packType) }
            }

            StarterPackContract.Event.OnPreviewClick -> {
                setEffect {
                    StarterPackContract.Effect.NavToPreview(
                        viewState.value.selectedPack?.id ?: 0
                    )
                }
            }

            is StarterPackContract.Event.OnTooltipToggle -> {
                setState { copy(showTooltip = event.show) }
            }

            StarterPackContract.Event.OnBackClick -> {
                setEffect { StarterPackContract.Effect.NavToBack }
            }
        }
    }
}