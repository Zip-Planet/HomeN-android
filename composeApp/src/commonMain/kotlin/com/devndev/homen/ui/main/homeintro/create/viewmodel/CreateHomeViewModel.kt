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
                setState { copy(selectedPack = event.packType) }
            }
            is CreateHomeContract.Event.OnRewardChanged -> {
                setState { copy(rewards = event.reward) }
            }
            is CreateHomeContract.Event.OnTooltipToggle -> {
                setState { copy(showTooltip = event.show) }
            }
            is CreateHomeContract.Event.OnChoreChecked -> {
                val currentSelected = viewState.value.selectedChores.toMutableList()
                if (currentSelected.contains(event.chore)) {
                    currentSelected.remove(event.chore)
                } else {
                    currentSelected.add(event.chore)
                }
                setState { copy(selectedChores = currentSelected) }
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
                val currentPack = viewState.value.selectedPack ?: StarterPackType.ROOMMATE
                val initialChores = getInitialChoresByPack(currentPack)
                
                setState { copy(chores = initialChores, selectedChores = initialChores) }
                setEffect { CreateHomeContract.Effect.NavToPreview }
            }
        }
    }

    private fun getInitialChoresByPack(packType: StarterPackType): List<Chore> {
        return when (packType) {
            StarterPackType.ROOMMATE -> listOf(
                Chore(title = "설거지 및 주방 마감", category = ChoreCategory.KITCHEN, days = DayOfWeek.entries.toSet(), difficulty = ChoreDifficulty.LOWER_MEDIUM),
                Chore(title = "거실 청소기 돌리기", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.LOWER_MEDIUM),
                Chore(title = "일반/음식물 쓰레기 버리기", category = ChoreCategory.TRASH, days = setOf(DayOfWeek.TUESDAY, DayOfWeek.FRIDAY), difficulty = ChoreDifficulty.MEDIUM),
                Chore(title = "공용 수건 세탁 및 건조대 널기", category = ChoreCategory.LAUNDRY, days = setOf(DayOfWeek.THURSDAY, DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.MEDIUM),
                Chore(title = "재활용 분리수거 및 박스 정리", category = ChoreCategory.TRASH, days = setOf(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.UPPER_MEDIUM),
                Chore(title = "화장실 전체 물청소", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.SATURDAY), difficulty = ChoreDifficulty.HIGH),
            )
            StarterPackType.DORMITORY -> listOf(
                Chore(title = "바닥 돌돌이(찍찍이) 밀기", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY), difficulty = ChoreDifficulty.LOW),
                Chore(title = "공용 테이블/책상 먼지 닦기", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.TUESDAY, DayOfWeek.SATURDAY), difficulty = ChoreDifficulty.LOW),
                Chore(title = "실내 환기 및 탈취제 뿌리기", category = ChoreCategory.CLEANING, days = DayOfWeek.entries.toSet(), difficulty = ChoreDifficulty.LOW),
                Chore(title = "공용 쓰레기통 모아서 비우기", category = ChoreCategory.TRASH, days = setOf(DayOfWeek.THURSDAY, DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.LOWER_MEDIUM),
                Chore(title = "욕실 배수구 머리카락 치우기", category = ChoreCategory.BATHROOM, days = setOf(DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY), difficulty = ChoreDifficulty.MEDIUM),
            )
            StarterPackType.MINIMAL -> listOf(
                Chore(title = "청소기 돌리기", category = ChoreCategory.CLEANING, days = setOf(DayOfWeek.SATURDAY), difficulty = ChoreDifficulty.LOWER_MEDIUM),
                Chore(title = "설거지 한 번에 하기", category = ChoreCategory.KITCHEN, days = setOf(DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.MEDIUM),
                Chore(title = "변기 및 세면대 행구기", category = ChoreCategory.BATHROOM, days = setOf(DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.MEDIUM),
                Chore(title = "집안 전체 쓰레기 묶어서 버리기", category = ChoreCategory.TRASH, days = setOf(DayOfWeek.SUNDAY), difficulty = ChoreDifficulty.UPPER_MEDIUM),
            )
        }
    }
}
