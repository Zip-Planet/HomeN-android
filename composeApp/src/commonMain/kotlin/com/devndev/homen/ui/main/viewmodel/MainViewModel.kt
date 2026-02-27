package com.devndev.homen.ui.main.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

/**
 * 메인 네비게이션 및 전역 상태를 관리
 */
class MainViewModel : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    override fun setInitialState() = MainContract.State()

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnHomeEntryComplete -> {
                setState { copy(hasHome = event.hasHome) }
            }
        }
    }
}
