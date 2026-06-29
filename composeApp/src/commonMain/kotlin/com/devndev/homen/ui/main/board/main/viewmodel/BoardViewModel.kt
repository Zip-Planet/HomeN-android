package com.devndev.homen.ui.main.board.main.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class BoardViewModel(

) : BaseViewModel<BoardContract.Event, BoardContract.State, BoardContract.Effect>() {
    override fun setInitialState() = BoardContract.State()

    override fun handleEvents(event: BoardContract.Event) {
        when (event) {
            BoardContract.Event.OnInit -> {
            }
        }
    }


}