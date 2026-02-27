package com.devndev.homen.ui.main.homeentry.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class HomeEntryViewModel : BaseViewModel<HomeEntryContract.Event, HomeEntryContract.State, HomeEntryContract.Effect>() {

    override fun setInitialState() = HomeEntryContract.State()

    override fun handleEvents(event: HomeEntryContract.Event) {
        when (event) {
            HomeEntryContract.Event.OnCreateHomeClick -> {
                setEffect { HomeEntryContract.Effect.NavigateToCreateHome }
            }
            HomeEntryContract.Event.OnJoinHomeClick -> {
                setEffect { HomeEntryContract.Effect.NavigateToJoinHome }
            }
        }
    }
}
