package com.devndev.homen.ui.main.homeentry.join.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class CodeEnterViewModel : BaseViewModel<CodeEnterContract.Event, CodeEnterContract.State, CodeEnterContract.Effect>() {

    override fun setInitialState() = CodeEnterContract.State()

    override fun handleEvents(event: CodeEnterContract.Event) {
        when (event) {
            is CodeEnterContract.Event.OnCodeChanged -> {
                setState { copy(code = event.code) }
            }
            CodeEnterContract.Event.OnJoinClick -> {
                // TODO: 서버 연동 및 코드 검증 로직 추가 예정
                setEffect { CodeEnterContract.Effect.NavigateToMain }
            }
            CodeEnterContract.Event.OnBackClick -> {
                setEffect { CodeEnterContract.Effect.PopBackStack }
            }
        }
    }
}
