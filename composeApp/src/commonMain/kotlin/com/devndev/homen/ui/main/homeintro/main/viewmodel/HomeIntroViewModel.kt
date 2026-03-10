package com.devndev.homen.ui.main.homeintro.main.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class HomeIntroViewModel : BaseViewModel<HomeIntroContract.Event, HomeIntroContract.State, HomeIntroContract.Effect>() {

    override fun setInitialState() = HomeIntroContract.State()

    override fun handleEvents(event: HomeIntroContract.Event) {
        when (event) {
            HomeIntroContract.Event.OnCreateHomeClick -> {
                setEffect { HomeIntroContract.Effect.NavigateToCreateHome }
            }
            HomeIntroContract.Event.OnJoinHomeClick -> {
                setEffect { HomeIntroContract.Effect.NavigateToJoinHome }
            }
        }
    }
}
