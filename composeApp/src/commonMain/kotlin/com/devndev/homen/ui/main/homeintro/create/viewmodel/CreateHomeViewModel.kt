package com.devndev.homen.ui.main.homeintro.create.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.chore.Chore
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.DayOfWeek
import com.devndev.homen.core.domain.model.chore.StarterPackType

class CreateHomeViewModel : BaseViewModel<CreateHomeContract.Event, CreateHomeContract.State, CreateHomeContract.Effect>() {

    override fun setInitialState() = CreateHomeContract.State()

    override fun handleEvents(event: CreateHomeContract.Event) {
        when (event) {
            is CreateHomeContract.Event.OnHomeNameChanged -> {
                setState { copy(homeName = event.name) }
            }
            is CreateHomeContract.Event.OnAvatarSelected -> {
                setState { copy(avatarId = event.avatarId) }
            }
            is CreateHomeContract.Event.OnPackSelected -> {
                val initialChores = getInitialChoresByPack(event.packType)
                setState { copy(selectedPack = event.packType, chores = initialChores) }
            }
            is CreateHomeContract.Event.OnRewardChanged -> {
                setState { copy(rewards = event.reward) }
            }
            is CreateHomeContract.Event.OnTooltipToggle -> {
                setState { copy(showTooltip = event.show) }
            }
            CreateHomeContract.Event.OnNextClick -> {
                setEffect { CreateHomeContract.Effect.NavToNext }
            }
            CreateHomeContract.Event.OnBackClick -> {
                setEffect { CreateHomeContract.Effect.PopBackStack }
            }
            CreateHomeContract.Event.OnCreateChoreClick -> {
                setEffect { CreateHomeContract.Effect.NavToCreateChore }
            }
            CreateHomeContract.Event.OnPreviewClick -> {
                setEffect { CreateHomeContract.Effect.NavToPreview }
            }
        }
    }

    private fun getInitialChoresByPack(packType: StarterPackType): List<Chore> {
        return when (packType) {
            StarterPackType.ROOMMATE -> listOf(
                Chore(title = "분리수거", category = ChoreCategory.TRASH, days = setOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY), difficulty = ChoreDifficulty.MEDIUM),
                Chore(title = "거실 청소", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.SATURDAY), difficulty = ChoreDifficulty.HIGH),
                Chore(title = "설거지", category = ChoreCategory.KITCHEN, days = DayOfWeek.entries.toSet(), difficulty = ChoreDifficulty.LOW)
            )
            StarterPackType.DORMITORY -> listOf(
                Chore(title = "방 청소", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.MEDIUM),
                Chore(title = "화장실 청소", category = ChoreCategory.BATHROOM, days = setOf(DayOfWeek.SATURDAY), difficulty = ChoreDifficulty.HIGH)
            )
            StarterPackType.MINIMAL -> listOf(
                Chore(title = "쓰레기 버리기", category = ChoreCategory.TRASH, days = setOf(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY), difficulty = ChoreDifficulty.LOW)
            )
        }
    }
}
