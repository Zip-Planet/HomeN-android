package com.devndev.homen.ui.main.assignment.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNPopup
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentContract
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentScreenType
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentTab
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentViewModel
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.assignment_add_chore_btn
import homen.composeapp.generated.resources.assignment_add_chore_floating_btn
import homen.composeapp.generated.resources.assignment_add_chore_msg
import homen.composeapp.generated.resources.assignment_add_chore_title
import homen.composeapp.generated.resources.assignment_confirm_next_week_popup_title
import homen.composeapp.generated.resources.assignment_confirm_popup_msg
import homen.composeapp.generated.resources.assignment_confirm_popup_title
import homen.composeapp.generated.resources.assignment_create_assignment_btn
import homen.composeapp.generated.resources.assignment_create_assignment_manager_btn
import homen.composeapp.generated.resources.assignment_create_assignment_manager_msg
import homen.composeapp.generated.resources.assignment_create_assignment_manager_title
import homen.composeapp.generated.resources.assignment_create_assignment_msg
import homen.composeapp.generated.resources.assignment_create_assignment_title
import homen.composeapp.generated.resources.assignment_create_next_assignment_manager_title
import homen.composeapp.generated.resources.assignment_create_next_assignment_title
import homen.composeapp.generated.resources.assignment_history_not_exist_title
import homen.composeapp.generated.resources.assignment_regenerate_popup_btn
import homen.composeapp.generated.resources.assignment_regenerate_popup_msg
import homen.composeapp.generated.resources.assignment_regenerate_popup_title
import homen.composeapp.generated.resources.cancel
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.confirm
import homen.composeapp.generated.resources.division_plan
import homen.composeapp.generated.resources.floating_btn_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AssignmentScreen(
    viewModel: AssignmentViewModel = koinViewModel(),
    initialTab: AssignmentTab,
    onNavToChoreManage: () -> Unit,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                AssignmentContract.Effect.NavigateToChoreManage -> {
                    onNavToChoreManage()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(AssignmentContract.Event.OnInit(initialTab))
    }

    if (uiState.isShowConfirmPopup) {
        val title = if (uiState.selectedTab == AssignmentTab.THIS_WEEK) {
            stringResource(Res.string.assignment_confirm_popup_title)
        } else {
            stringResource(Res.string.assignment_confirm_next_week_popup_title)
        }
        HomeNPopup(
            title = title,
            message = stringResource(Res.string.assignment_confirm_popup_msg),
            startButtonText = stringResource(Res.string.cancel),
            endButtonText = stringResource(Res.string.confirm),
            onStartButtonClick = {
                viewModel.setEvent(AssignmentContract.Event.OnDismissPopup)
            },
            onEndButtonClick = {
                viewModel.setEvent(AssignmentContract.Event.OnConfirmClick)
            },
            isTwoButton = true,
            onDismiss = {
                viewModel.setEvent(AssignmentContract.Event.OnDismissPopup)
            }
        )
    }

    if (uiState.isShowRegeneratePopup) {
        HomeNPopup(
            title = stringResource(Res.string.assignment_regenerate_popup_title),
            message = stringResource(Res.string.assignment_regenerate_popup_msg),
            startButtonText = stringResource(Res.string.assignment_regenerate_popup_btn),
            onStartButtonClick = {
                viewModel.setEvent(AssignmentContract.Event.OnRegenerateClick)
            },
            isTwoButton = false,
            onDismiss = {
                viewModel.setEvent(AssignmentContract.Event.OnDismissPopup)
            }
        )
    }


    HomeNScreen(
        topBar = {
            NotificationTopBar(
                title = stringResource(Res.string.division_plan),
                onNotificationClick = {}
            )
        },
        isLoading = uiState.isLoading,
        mainIsLoading = uiState.mainIsLoading,
        isNeedBottomExpanded = true
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(22.dp))

                AssignmentTabHeader(
                    selectedTab = uiState.selectedTab,
                    onTabSelected = { tab ->
                        viewModel.setEvent(AssignmentContract.Event.OnTabSelected(tab))
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                when (uiState.selectedTab) {
                    AssignmentTab.THIS_WEEK,
                    AssignmentTab.NEXT_WEEK -> {
                        when (uiState.screenType) {
                            AssignmentScreenType.NONE -> {

                            }

                            AssignmentScreenType.ADD_CHORE -> {
                                if (uiState.isManager) {
                                    NotAssignmentContent(
                                        icon = Res.drawable.clipboard_icon,
                                        title = stringResource(Res.string.assignment_add_chore_title),
                                        message = stringResource(Res.string.assignment_add_chore_msg),
                                        buttonText = stringResource(Res.string.assignment_add_chore_btn),
                                    ) {
                                        viewModel.setEvent(AssignmentContract.Event.OnAddChoreClick)
                                    }
                                } else {
                                    NotAssignmentContent(
                                        icon = Res.drawable.chart_icon,
                                        title = stringResource(Res.string.assignment_create_assignment_title),
                                        message = stringResource(Res.string.assignment_create_assignment_msg),
                                        buttonText = stringResource(Res.string.assignment_create_assignment_btn),
                                    ) {

                                    }
                                }
                            }

                            AssignmentScreenType.CREATE_ASSIGNMENT -> {
                                if (uiState.isManager) {
                                    val title =
                                        if (uiState.selectedTab == AssignmentTab.THIS_WEEK) {
                                            stringResource(Res.string.assignment_create_assignment_manager_title)
                                        } else {
                                            stringResource(Res.string.assignment_create_next_assignment_manager_title)
                                        }
                                    NotAssignmentContent(
                                        icon = Res.drawable.chart_icon,
                                        title = title,
                                        message = stringResource(Res.string.assignment_create_assignment_manager_msg),
                                        buttonText = stringResource(Res.string.assignment_create_assignment_manager_btn),
                                    ) {
                                        viewModel.setEvent(AssignmentContract.Event.OnCreateAssignmentClick)
                                    }
                                } else {
                                    val title =
                                        if (uiState.selectedTab == AssignmentTab.THIS_WEEK) {
                                            stringResource(Res.string.assignment_create_assignment_title)
                                        } else {
                                            stringResource(Res.string.assignment_create_next_assignment_title)
                                        }

                                    NotAssignmentContent(
                                        icon = Res.drawable.chart_icon,
                                        title = title,
                                        message = stringResource(Res.string.assignment_create_assignment_msg),
                                        buttonText = stringResource(Res.string.assignment_create_assignment_btn),
                                    ) {

                                    }
                                }
                            }

                            AssignmentScreenType.ASSIGNMENT -> {
                                AssignmentContent(
                                    uiState = uiState,
                                    onMemberClick = {
                                        viewModel.setEvent(
                                            AssignmentContract.Event.OnSelectedMember(
                                                it
                                            )
                                        )
                                    },
                                    onConfirmClick = {
                                        viewModel.setEvent(AssignmentContract.Event.OnConfirmButtonClick)
                                    }
                                )
                            }
                        }
                    }

                    AssignmentTab.HISTORY -> {
                        when (uiState.screenType) {
                            AssignmentScreenType.NONE -> {}
                            AssignmentScreenType.ADD_CHORE,
                            AssignmentScreenType.CREATE_ASSIGNMENT -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth().
                                        padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.chart_icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.Black
                                    )

                                    Text(
                                        text = stringResource(Res.string.assignment_history_not_exist_title),
                                        style = HomeNTheme.typography.suitExtraBold,
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )

                                    Spacer(modifier = Modifier.weight(1f))


                                    HistoryWeekSelector(
                                        weekOffset = uiState.weekOffset,
                                        onWeekSelected = {
                                            viewModel.setEvent(
                                                AssignmentContract.Event.OnWeekSelected(
                                                    it
                                                )
                                            )
                                        }
                                    )

                                }
                                Spacer(modifier = Modifier.height(25.dp))

                                Text(
                                    modifier = Modifier.padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                                    text = stringResource(Res.string.assignment_history_not_exist_title),
                                    style = HomeNTheme.typography.suitRegular,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            }

                            AssignmentScreenType.ASSIGNMENT -> {
                                AssignmentContent(
                                    uiState = uiState,
                                    onMemberClick = {
                                        viewModel.setEvent(
                                            AssignmentContract.Event.OnSelectedMember(
                                                it
                                            )
                                        )
                                    },
                                    onWeekSelected = {
                                        viewModel.setEvent(
                                            AssignmentContract.Event.OnWeekSelected(
                                                it
                                            )
                                        )
                                    },
                                    onConfirmClick = {
                                        viewModel.setEvent(AssignmentContract.Event.OnConfirmButtonClick)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if (uiState.isAddButtonExist) {
                Row(
                    modifier = Modifier
                        .padding(end = HomeNTheme.dimensions.horizontalPadding, bottom = 30.dp)
                        .align(Alignment.BottomEnd),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.assignment_add_chore_floating_btn),
                        style = HomeNTheme.typography.suitSemiBold,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    Icon(
                        painter = painterResource(Res.drawable.floating_btn_icon),
                        contentDescription = "add chore icon",
                        modifier = Modifier
                            .size(51.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                viewModel.setEvent(AssignmentContract.Event.OnAddChoreClick)
                            }
                    )
                }
            }
        }
    }
}


@Composable
fun AssignmentTabHeader(
    selectedTab: AssignmentTab,
    onTabSelected: (AssignmentTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
            .fillMaxWidth()
            .background(ButtonGray, RoundedCornerShape(10.dp))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AssignmentTab.entries.forEach { tab ->
            val isSelected = tab == selectedTab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(31.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onTabSelected(tab) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.title,
                    style = if (isSelected) HomeNTheme.typography.suitBold else HomeNTheme.typography.suitRegular,
                    color = if (isSelected) Color.Black else BottomGray,
                    fontSize = 15.sp
                )
            }
        }
    }
}