package com.devndev.homen.ui.main.home.starterpackpreview.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.RepeatDay
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.usecase.home.CreateChoreUseCase
import kotlinx.coroutines.launch

class StarterPackPreviewViewModel(
    private val createChoreUseCase: CreateChoreUseCase
) : BaseViewModel<StarterPackPreviewContract.Event, StarterPackPreviewContract.State, StarterPackPreviewContract.Effect>() {
    override fun setInitialState() = StarterPackPreviewContract.State()

    override fun handleEvents(event: StarterPackPreviewContract.Event) {
        when (event) {
            StarterPackPreviewContract.Event.OnBackClick -> {
                setEffect { StarterPackPreviewContract.Effect.NavToBack }
            }
            is StarterPackPreviewContract.Event.OnChoreChecked -> {
                val currentSelected = viewState.value.selectedChores.toMutableList()
                if (currentSelected.contains(event.chore)) {
                    currentSelected.remove(event.chore)
                } else {
                    currentSelected.add(event.chore)
                }
                setState { copy(selectedChores = currentSelected) }
            }

            is StarterPackPreviewContract.Event.OnInit -> {
                val packType = StarterPackType.fromValue(event.packType) ?: StarterPackType.ROOMMATE
                val initialChores = getInitialChoresByPack(packType)
                setState { copy(chores = initialChores, selectedChores = initialChores, selectedPack = packType) }
            }

            StarterPackPreviewContract.Event.OnApplyClick -> {
                createChore()
            }

            is StarterPackPreviewContract.Event.OnTooltipToggle -> {
                setState { copy(showTooltip = event.show) }
            }
        }
    }

    private fun getInitialChoresByPack(packType: StarterPackType): List<Chore> {
        return when (packType) {
            StarterPackType.ROOMMATE -> listOf(
                Chore(
                    name = "설거지 및 주방 마감",
                    category = ChoreCategory.KITCHEN.id,
                    repeatDays = RepeatDay.entries.map { it.value },
                    difficulty = ChoreDifficulty.LOWER_MEDIUM
                ),
                Chore(
                    name = "거실 청소기 돌리기",
                    category = ChoreCategory.CLEANING.id,
                    repeatDays = listOf(RepeatDay.WEDNESDAY.value, RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.LOWER_MEDIUM
                ),
                Chore(
                    name = "일반/음식물 쓰레기 버리기",
                    category = ChoreCategory.TRASH.id,
                    repeatDays = listOf(RepeatDay.TUESDAY.value, RepeatDay.FRIDAY.value),
                    difficulty = ChoreDifficulty.MEDIUM
                ),
                Chore(
                    name = "공용 수건 세탁 및 건조대 널기",
                    category = ChoreCategory.LAUNDRY.id,
                    repeatDays = listOf(RepeatDay.THURSDAY.value, RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.MEDIUM
                ),
                Chore(
                    name = "재활용 분리수거 및 박스 정리",
                    category = ChoreCategory.TRASH.id,
                    repeatDays = listOf(RepeatDay.WEDNESDAY.value, RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.UPPER_MEDIUM
                ),
                Chore(
                    name = "화장실 전체 물청소",
                    category = ChoreCategory.BATHROOM.id,
                    repeatDays = listOf(RepeatDay.SATURDAY.value),
                    difficulty = ChoreDifficulty.HIGH
                ),
            )

            StarterPackType.DORMITORY -> listOf(
                Chore(
                    name = "바닥 돌돌이(찍찍이) 밀기",
                    category = ChoreCategory.CLEANING.id,
                    repeatDays = listOf(
                        RepeatDay.MONDAY.value,
                        RepeatDay.WEDNESDAY.value,
                        RepeatDay.FRIDAY.value
                    ),
                    difficulty = ChoreDifficulty.LOW
                ),
                Chore(
                    name = "공용 테이블/책상 먼지 닦기",
                    category = ChoreCategory.CLEANING.id,
                    repeatDays = listOf(RepeatDay.TUESDAY.value, RepeatDay.SATURDAY.value),
                    difficulty = ChoreDifficulty.LOW
                ),
                Chore(
                    name = "실내 환기 및 탈취제 뿌리기",
                    category = ChoreCategory.CLEANING.id,
                    repeatDays = RepeatDay.entries.map { it.value },
                    difficulty = ChoreDifficulty.LOW
                ),
                Chore(
                    name = "공용 쓰레기통 모아서 비우기",
                    category = ChoreCategory.TRASH.id,
                    repeatDays = listOf(RepeatDay.THURSDAY.value, RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.LOWER_MEDIUM
                ),
                Chore(
                    name = "욕실 배수구 머리카락 치우기",
                    category = ChoreCategory.BATHROOM.id,
                    repeatDays = listOf(RepeatDay.WEDNESDAY.value, RepeatDay.SATURDAY.value),
                    difficulty = ChoreDifficulty.MEDIUM
                ),
            )

            StarterPackType.MINIMAL -> listOf(
                Chore(
                    name = "청소기 돌리기",
                    category = ChoreCategory.CLEANING.id,
                    repeatDays = listOf(RepeatDay.SATURDAY.value),
                    difficulty = ChoreDifficulty.LOWER_MEDIUM
                ),
                Chore(
                    name = "설거지 한 번에 하기",
                    category = ChoreCategory.KITCHEN.id,
                    repeatDays = listOf(RepeatDay.WEDNESDAY.value, RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.MEDIUM
                ),
                Chore(
                    name = "변기 및 세면대 행구기",
                    category = ChoreCategory.BATHROOM.id,
                    repeatDays = listOf(RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.MEDIUM
                ),
                Chore(
                    name = "집안 전체 쓰레기 묶어서 버리기",
                    category = ChoreCategory.TRASH.id,
                    repeatDays = listOf(RepeatDay.SUNDAY.value),
                    difficulty = ChoreDifficulty.UPPER_MEDIUM
                ),
            )
        }
    }

    private fun createChore() {
        viewModelScope.launch {
            val result = createChoreUseCase(viewState.value.selectedChores)
            when (result) {
                is ApiResult.Success -> {
                    setEffect { StarterPackPreviewContract.Effect.NavToCreateChore }
                }
                is ApiResult.Error -> {

                }
                ApiResult.NetworkError -> {

                }
            }
        }
    }
}