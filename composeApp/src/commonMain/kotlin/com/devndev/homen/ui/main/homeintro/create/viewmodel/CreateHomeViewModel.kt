package com.devndev.homen.ui.main.homeintro.create.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class CreateHomeViewModel : BaseViewModel<CreateHomeContract.Event, CreateHomeContract.State, CreateHomeContract.Effect>() {

    override fun setInitialState() = CreateHomeContract.State()

    override fun handleEvents(event: CreateHomeContract.Event) {
        when (event) {
            is CreateHomeContract.Event.OnHomeNameChanged -> {
                setState { copy(homeName = event.name) }
            }
            is CreateHomeContract.Event.OnAvatarSelected -> {
                setState { copy(avatarId = event.avatarId) }
            }
            is CreateHomeContract.Event.OnPackSelected -> {
                setState { copy(selectedPackId = event.packId) }
            }
            is CreateHomeContract.Event.OnRewardChanged -> {
                setState { copy(rewards = event.reward) }
            }
            CreateHomeContract.Event.OnNextClick -> {
                setEffect { CreateHomeContract.Effect.NavToNext }
            }
            CreateHomeContract.Event.OnBackClick -> {
                setEffect { CreateHomeContract.Effect.PopBackStack }
            }
        }
    }
}
