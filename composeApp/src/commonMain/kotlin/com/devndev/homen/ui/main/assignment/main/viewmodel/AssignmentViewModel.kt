package com.devndev.homen.ui.main.assignment.main.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class AssignmentViewModel(

) : BaseViewModel<AssignmentContract.Event, AssignmentContract.State, AssignmentContract.Effect>() {
    override fun setInitialState() = AssignmentContract.State()

    override fun handleEvents(event: AssignmentContract.Event) {
        when (event) {
            AssignmentContract.Event.OnInit -> {

            }
            is AssignmentContract.Event.OnTabSelected -> {
                setState { copy(selectedTab = event.tab) }
            }
        }
    }
}