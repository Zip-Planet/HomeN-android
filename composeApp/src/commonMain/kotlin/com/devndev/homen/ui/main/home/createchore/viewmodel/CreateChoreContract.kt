package com.devndev.homen.ui.main.home.createchore.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.RepeatDay
import com.devndev.homen.core.domain.model.home.Chore

class CreateChoreContract {
    sealed class Event : ViewEvent {
        data object OnBackClick : Event()
        data class OnCategoryClick(val choreCategory: ChoreCategory) : Event()
        data class OnTitleChange(val title: String) : Event()
        data class OnDescriptionChange(val description: String) : Event()
        data class OnDayClick(val day: RepeatDay) : Event()
        data class OnDifficultyClick(val difficulty: ChoreDifficulty) : Event()
        data class OnSaveClick(val isEdit: Boolean, val id: Int? = null) : Event()
        data class OnEdit(val id: Int) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val selectedCategory: ChoreCategory? = null,
        val title: String = "",
        val description: String = "",
        val selectedDays: Set<Int> = emptySet(),
        val selectedDifficulty: ChoreDifficulty? = null
    ) : ViewState {
        val isSaveButtonEnabled =
            title.isNotBlank()
                    && selectedCategory != null
                    && description.isNotBlank()
                    && selectedDays.isNotEmpty()
                    && selectedDifficulty != null
    }

    sealed class Effect : ViewSideEffect {
        data object NavigateToBack : Effect()
    }
}