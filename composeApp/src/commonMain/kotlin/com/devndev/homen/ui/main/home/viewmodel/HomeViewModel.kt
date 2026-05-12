package com.devndev.homen.ui.main.home.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class HomeViewModel(

): BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {
    override fun setInitialState() = HomeContract.State()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnMemberSelected -> {
                setState { copy(selectedMember = event.name) }
            }
        }
    }
}