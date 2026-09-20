package com.devndev.homen.ui.main.board.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import homen.composeapp.generated.resources.board_make_request_exchange_btn
import homen.composeapp.generated.resources.board_request_exchange_descirption_hint
import homen.composeapp.generated.resources.board_request_exchange_my_chore_title
import homen.composeapp.generated.resources.board_request_exchange_my_selector_done_title
import homen.composeapp.generated.resources.board_request_exchange_my_selector_title
import homen.composeapp.generated.resources.board_request_exchange_target_chore_title
import homen.composeapp.generated.resources.board_request_exchange_target_selector_done_title
import homen.composeapp.generated.resources.board_request_exchange_target_selector_title
import homen.composeapp.generated.resources.board_request_help_descirption_label
import homen.composeapp.generated.resources.board_request_help_descirption_title
import homen.composeapp.generated.resources.calendar_icon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BoardExchangeContent(
    weekDay: String,
    myAssignments: List<AssignmentItem>,
    targetAssignments: List<AssignmentItem>,
    onMyAssignmentSelected: (AssignmentItem) -> Unit = {},
    onTargetAssignmentSelected: (AssignmentItem) -> Unit = {},
    isRequestable: Boolean,
    onExchangeMessageChanged: (String) -> Unit = {},
    exchangeMessage: String,
    onRequestExchangeClick: () -> Unit = {}
) {
    val mySelectorInitTitle = if (myAssignments.isNotEmpty()) {
        stringResource(Res.string.board_request_exchange_my_selector_title)
    } else {
        stringResource(Res.string.board_request_exchange_my_selector_done_title)
    }

    val targetSelectorInitTitle = if (targetAssignments.isNotEmpty()) {
        stringResource(Res.string.board_request_exchange_target_selector_title)
    } else {
        stringResource(Res.string.board_request_exchange_target_selector_done_title)
    }

    var mySelectorTitle by remember { mutableStateOf(mySelectorInitTitle) }
    var targetSelectorTitle by remember { mutableStateOf(targetSelectorInitTitle) }

    Column(
        modifier = Modifier
            .padding(top = 42.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            text = stringResource(Res.string.board_request_exchange_my_chore_title),
            style = HomeNTheme.typography.suitBold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        ChoreSelector(
            title = mySelectorTitle,
            assignments = myAssignments,
            onChoreSelected = {
                mySelectorTitle = it.choreName
                onMyAssignmentSelected(it)
            },
            isExpandable = myAssignments.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(42.dp))

        Text(
            text = stringResource(Res.string.board_request_exchange_target_chore_title),
            style = HomeNTheme.typography.suitBold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        ChoreSelector(
            title = targetSelectorTitle,
            assignments = targetAssignments,
            onChoreSelected = {
                targetSelectorTitle = it.choreName
                onTargetAssignmentSelected(it)
            },
            isExpandable = targetAssignments.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(Res.string.board_request_help_descirption_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = stringResource(Res.string.board_request_help_descirption_label),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        HomeNLongTextField(
            value = exchangeMessage,
            onValueChange = onExchangeMessageChanged,
            hint = stringResource(Res.string.board_request_exchange_descirption_hint),
            maxChar = 30
        )

        Spacer(modifier = Modifier.weight(1f))

        HomeNButton(
            text = stringResource(Res.string.board_make_request_exchange_btn),
            onClick = onRequestExchangeClick,
            enabled = isRequestable
        )
    }
}

@Preview
@Composable
fun BoardExchangeContentPreview() {
    HomeNTheme {
        BoardExchangeContent(
            weekDay = "2026년 1월 5주차",
            myAssignments = emptyList(),
            targetAssignments = emptyList(),
            isRequestable = false,
            exchangeMessage = ""
        )
    }
}
