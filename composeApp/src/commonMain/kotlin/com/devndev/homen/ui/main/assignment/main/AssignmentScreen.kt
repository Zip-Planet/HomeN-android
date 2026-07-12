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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentContract
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentViewModel
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.assignment_add_chore_btn
import homen.composeapp.generated.resources.assignment_add_chore_floating_btn
import homen.composeapp.generated.resources.assignment_add_chore_msg
import homen.composeapp.generated.resources.assignment_add_chore_title
import homen.composeapp.generated.resources.assignment_create_assignment_btn
import homen.composeapp.generated.resources.assignment_create_assignment_manager_btn
import homen.composeapp.generated.resources.assignment_create_assignment_manager_msg
import homen.composeapp.generated.resources.assignment_create_assignment_manager_title
import homen.composeapp.generated.resources.assignment_create_assignment_msg
import homen.composeapp.generated.resources.assignment_create_assignment_title
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.division_plan
import homen.composeapp.generated.resources.floating_btn_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AssignmentScreen(
    viewModel: AssignmentViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {

                else -> {}
            }
        }
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

                when (uiState.screenType) {
                    AssignmentContract.AssignmentScreenType.NONE -> {

                    }

                    AssignmentContract.AssignmentScreenType.ADD_CHORE -> {
                        if (uiState.isManager) {
                            NotAssignmentContent(
                                icon = Res.drawable.clipboard_icon,
                                title = stringResource(Res.string.assignment_add_chore_title),
                                message = stringResource(Res.string.assignment_add_chore_msg),
                                buttonText = stringResource(Res.string.assignment_add_chore_btn),
                            ) {

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

                    AssignmentContract.AssignmentScreenType.CREATE_ASSIGNMENT -> {
                        if (uiState.isManager) {
                            NotAssignmentContent(
                                icon = Res.drawable.chart_icon,
                                title = stringResource(Res.string.assignment_create_assignment_manager_title),
                                message = stringResource(Res.string.assignment_create_assignment_manager_msg),
                                buttonText = stringResource(Res.string.assignment_create_assignment_manager_btn),
                            ) {

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

                            }
                    )
                }
            }
        }
    }
}


@Composable
fun AssignmentTabHeader(
    selectedTab: AssignmentContract.AssignmentTab,
    onTabSelected: (AssignmentContract.AssignmentTab) -> Unit,
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
        AssignmentContract.AssignmentTab.entries.forEach { tab ->
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

@Composable
fun NotAssignmentContent(
    icon: DrawableResource,
    title: String,
    message: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.5.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(22.dp)
            )

            Text(
                text = title,
                style = HomeNTheme.typography.suitExtraBold,
                color = Color.Black,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = message,
            style = HomeNTheme.typography.suitRegular,
            color = Color.Black,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        HomeNButton(
            text = buttonText,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun AddChoreContentPreview() {
    NotAssignmentContent(
        icon = Res.drawable.clipboard_icon,
        title = stringResource(Res.string.assignment_add_chore_title),
        message = stringResource(Res.string.assignment_add_chore_msg),
        buttonText = stringResource(Res.string.assignment_add_chore_btn),
    ) {

    }
}