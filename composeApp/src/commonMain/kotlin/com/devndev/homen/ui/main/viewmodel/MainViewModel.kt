package com.devndev.homen.ui.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.common.util.Logger
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import kotlinx.coroutines.launch

/**
 * 메인 네비게이션 및 전역 상태를 관리
 */
class MainViewModel(
    private val getHomeUseCase: GetHomeUseCase
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    override fun setInitialState() = MainContract.State()

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnMainNav -> {
                getHome()
            }
        }
    }


    private fun getHome() {
        viewModelScope.launch {
            when (val result = getHomeUseCase()) {
                is ApiResult.Success -> {
                    Logger.d(TAG, "getHome" )
                    setState { copy(hasHome = true) }
                }
                is ApiResult.Error -> {
                    Logger.d(TAG, "getHome ${result.code}" )
                    if (result.code == 404) {
                        setState { copy(hasHome = true) }
                    }
                }
                ApiResult.NetworkError -> {
                    Logger.d(TAG, "getHome NetworkError" )
                }
            }
        }
    }

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}
