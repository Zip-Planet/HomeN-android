package com.devndev.homen.ui.main.home.createchore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.chore.ChoreCategory
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.chore.RepeatDay
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNLongTextField
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.createchore.viewmodel.CreateChoreContract
import com.devndev.homen.ui.main.home.createchore.viewmodel.CreateChoreViewModel
import com.devndev.homen.ui.theme.Blue4736FC
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.crate_chore_description_hint
import homen.composeapp.generated.resources.crate_chore_description_section
import homen.composeapp.generated.resources.create_chore_category_section
import homen.composeapp.generated.resources.create_chore_day_section
import homen.composeapp.generated.resources.create_chore_difficulty_description
import homen.composeapp.generated.resources.create_chore_difficulty_point
import homen.composeapp.generated.resources.create_chore_difficulty_section
import homen.composeapp.generated.resources.create_chore_title
import homen.composeapp.generated.resources.create_chore_title_hint
import homen.composeapp.generated.resources.create_chore_title_section
import homen.composeapp.generated.resources.save_button
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateChoreScreen(
    viewModel: CreateChoreViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CreateChoreContract.Effect.NavigateToBack -> onBackClick()
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.create_chore_title),
                onBackClick = { viewModel.setEvent(CreateChoreContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = HomeNTheme.dimensions.bottomPadding)
                .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(42.dp))

                Text(
                    text = stringResource(Res.string.create_chore_category_section),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChoreCategory.entries.forEach {
                        CategoryItem(
                            choreCategory = it,
                            isSelected = uiState.selectedCategory == it,
                            onClick = {
                                viewModel.setEvent(CreateChoreContract.Event.OnCategoryClick(it))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = stringResource(Res.string.create_chore_title_section),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                HomeNLongTextField(
                    value = uiState.title,
                    onValueChange = {
                        viewModel.setEvent(CreateChoreContract.Event.OnTitleChange(it))
                    },
                    hint = stringResource(Res.string.create_chore_title_hint),
                    maxChar = 20,
                    enabled = true,
                    regex = null
                )

                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = stringResource(Res.string.crate_chore_description_section),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                HomeNLongTextField(
                    value = uiState.description,
                    onValueChange = {
                        viewModel.setEvent(CreateChoreContract.Event.OnDescriptionChange(it))
                    },
                    hint = stringResource(Res.string.crate_chore_description_hint),
                    maxChar = 20,
                    enabled = true,
                    regex = null
                )

                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = stringResource(Res.string.create_chore_day_section),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RepeatDay.entries.forEach { day ->
                        CircleChip(
                            text = day.day,
                            isSelected = uiState.selectedDays.contains(day.value),
                            onCircleClick = {
                                viewModel.setEvent(CreateChoreContract.Event.OnDayClick(day))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(45.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.create_chore_difficulty_section),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    if (uiState.selectedDifficulty != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(Res.string.create_chore_difficulty_point).replace(
                                "n",
                                uiState.selectedDifficulty?.point.toString()
                            ),
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 18.sp,
                            color = Blue4736FC
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(Res.string.create_chore_difficulty_description),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ChoreDifficulty.entries.forEach { difficulty ->
                        CircleChip(
                            text = difficulty.label,
                            isSelected = uiState.selectedDifficulty?.id == difficulty.id,
                            onCircleClick = {
                                viewModel.setEvent(
                                    CreateChoreContract.Event.OnDifficultyClick(
                                        difficulty
                                    )
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
            HomeNButton(
                text = stringResource(Res.string.save_button),
                onClick = {
                    viewModel.setEvent(CreateChoreContract.Event.OnSaveClick)
                },
                enabled = uiState.isSaveButtonEnabled
            )
        }
    }
}

@Composable
fun CategoryItem(
    choreCategory: ChoreCategory,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(Color.White)
                .then(
                    if (isSelected) Modifier.border(1.dp, Color.Black, CircleShape)
                    else Modifier
                )
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(choreCategory.resource),
                contentDescription = null,
                modifier = Modifier.size(33.dp)
            )
        }

        Text(
            text = choreCategory.label,
            style = HomeNTheme.typography.suitMedium,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun CircleChip(
    text: String,
    isSelected: Boolean,
    onCircleClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(26.dp)
            .clip(RoundedCornerShape(13.dp))
            .background(if (isSelected) Color.Black else Color.White)
            .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(13.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCircleClick(text)
            }
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center) {
        Text(
            text = text,
            style = HomeNTheme.typography.suitRegular,
            fontSize = 16.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}