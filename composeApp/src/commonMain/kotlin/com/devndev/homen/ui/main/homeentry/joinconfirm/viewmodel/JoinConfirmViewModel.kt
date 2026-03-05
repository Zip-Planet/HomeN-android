package com.devndev.homen.ui.main.homeentry.joinconfirm.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class JoinConfirmViewModel : BaseViewModel<JoinConfirmContract.Event, JoinConfirmContract.State, JoinConfirmContract.Effect>() {

    override fun setInitialState() = JoinConfirmContract.State()

    override fun handleEvents(event: JoinConfirmContract.Event) {
        when (event) {
            JoinConfirmContract.Event.OnJoinClick -> {
                // TODO: 실제 집 참여 API 호출 로직 추가 예정
                setEffect { JoinConfirmContract.Effect.NavigateToDone }
            }
            JoinConfirmContract.Event.OnBackClick -> {
                setEffect { JoinConfirmContract.Effect.PopBackStack }
            }
        }
    }
}
