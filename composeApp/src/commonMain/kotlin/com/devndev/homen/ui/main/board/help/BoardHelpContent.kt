package com.devndev.homen.ui.main.board.help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.ui.component.ChoreSelector
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNLongTextField
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.board_make_request_help_btn
import homen.composeapp.generated.resources.board_request_help_chore_title
import homen.composeapp.generated.resources.board_request_help_descirption_hint
import homen.composeapp.generated.resources.board_request_help_descirption_label
import homen.composeapp.generated.resources.board_request_help_descirption_title
import homen.composeapp.generated.resources.board_request_help_selector_done_title
import homen.composeapp.generated.resources.board_request_help_selector_title
import homen.composeapp.generated.resources.calendar_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoardHelpContent(
    weekDay: String,
    assignments: List<AssignmentItem>,
    onAssignmentSelected: (AssignmentItem) -> Unit = {},
    isRequestable: Boolean,
    onHelpMessageChanged: (String) -> Unit = {},
    helpMessage: String,
    onRequestHelpClick: () -> Unit = {}
) {
    val selectorInitTitle = if (assignments.isNotEmpty()) {
        stringResource(Res.string.board_request_help_selector_title)
    } else {
        stringResource(Res.string.board_request_help_selector_done_title)
    }

    var selectorTitle by remember { mutableStateOf(selectorInitTitle) }

    Column(
        modifier = Modifier
            .padding(top = 42.dp)
            .fillMaxSize()
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
            .padding(bottom = HomeNTheme.dimensions.bottomPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.calendar_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = weekDay,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(Res.string.board_request_help_chore_title),
            style = HomeNTheme.typography.suitBold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        ChoreSelector(
            title = selectorTitle,
            assignments = assignments,
            onChoreSelected = {
                selectorTitle = it.choreName
                onAssignmentSelected(it)
            },
            isExpandable = assignments.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(42.dp))

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = stringResource(Res.string.board_request_help_descirption_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Text(
                text = stringResource(Res.string.board_request_help_descirption_label),
                style = HomeNTheme.typography.suitBold,
                fontSize = 10.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        HomeNLongTextField(
            value = helpMessage,
            onValueChange = {
                onHelpMessageChanged(it)
            },
            hint = stringResource(Res.string.board_request_help_descirption_hint),
            maxChar = 30,
            enabled = true,
            regex = null
        )

        Spacer(modifier = Modifier.weight(1f))

        HomeNButton(
            text = stringResource(Res.string.board_make_request_help_btn),
            onClick = onRequestHelpClick,
            enabled = isRequestable
        )
    }
}

@Preview
@Composable
fun BoardHelpContentPreview() {
    HomeNTheme {
        BoardHelpContent(
            weekDay = "2026년 1월 5주차",
            assignments = List(10) { index ->
                AssignmentItem(
                    id = index,
                    homeChoreId = index,
                    weekday = index % 7,
                    weekdayLabel = listOf("월", "화", "수", "목", "금", "토", "일")[index % 7],
                    choreName = "집안일 $index",
                    category = (index % 5) + 1,
                    categoryLabel = "카테고리",
                    difficulty = (index % 3) + 1,
                    difficultyLabel = listOf("쉬움", "중간", "어려움")[index % 3],
                    point = 10 * (index + 1),
                    assignee = null,
                    date = "2026-09-01",
                    isCompleted = false,
                    changeType = null
                )
            },
            isRequestable = true,
            helpMessage = ""
        )
    }
}