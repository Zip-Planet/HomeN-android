package com.devndev.homen.ui.main.homeentry.joindone.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class JoinDoneViewModel : BaseViewModel<JoinDoneContract.Event, JoinDoneContract.State, JoinDoneContract.Effect>() {

    override fun setInitialState() = JoinDoneContract.State()

    override fun handleEvents(event: JoinDoneContract.Event) {
        when (event) {
            JoinDoneContract.Event.OnConfirmClick -> {
                setEffect { JoinDoneContract.Effect.NavigateToHome }
            }
        }
    }
}
