package com.devndev.homen.ui.main.home.choremanage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageContract
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chore_add_option_description
import homen.composeapp.generated.resources.chore_add_option_title
import homen.composeapp.generated.resources.chore_empty_title
import homen.composeapp.generated.resources.chore_manage_tooltip_msg
import homen.composeapp.generated.resources.chore_manage_tooltip_title
import homen.composeapp.generated.resources.chore_starter_pack_option_description
import homen.composeapp.generated.resources.chore_starter_pack_option_title
import homen.composeapp.generated.resources.edit_alt_icon
import homen.composeapp.generated.resources.folder_user_icon
import homen.composeapp.generated.resources.next_button
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChoreManageEmptyScreen(
    uiState: ChoreManageContract.State,
    onTooltipClick: (Boolean) -> Unit,
    onSelectOption: (Int) -> Unit,
    onNextButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = HomeNTheme.dimensions.bottomPadding)
        ) {
            Text(
                text = stringResource(Res.string.chore_empty_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(32.dp))
            ChoreManageOptionCard(
                title = stringResource(Res.string.chore_add_option_title),
                description = stringResource(Res.string.chore_add_option_description),
                icon = Res.drawable.edit_alt_icon,
                isSelected = uiState.selectedOption == 1,
                onClick = {
                    onSelectOption(1)
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            ChoreManageOptionCard(
                title = stringResource(Res.string.chore_starter_pack_option_title),
                description = stringResource(Res.string.chore_starter_pack_option_description),
                icon = Res.drawable.folder_user_icon,
                isSelected = uiState.selectedOption == 2,
                onClick = {
                    onSelectOption(2)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            if (uiState.selectedOption != 0) {
                HomeNButton(
                    text = stringResource(Res.string.next_button),
                    onClick = {
                        onNextButtonClick()
                    },
                    enabled = true
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 1.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TooltipButton {
                onTooltipClick(!uiState.isEmptyChoreTooltipShow)
            }

            if (uiState.isEmptyChoreTooltipShow) {
                HomeNTooltip(
                    title = stringResource(Res.string.chore_manage_tooltip_title),
                    messages = listOf(stringResource(Res.string.chore_manage_tooltip_msg)),
                    onCloseClick = {
                        onTooltipClick(false)

                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { /* 툴팁 내부 클릭 시 닫히지 않도록 보호 */ }
                    }
                )
            }
        }
    }
}

@Composable
fun ChoreManageOptionCard(
    title: String,
    description: String,
    icon: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Black else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 31.dp, horizontal = 21.5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = HomeNTheme.typography.suitExtraBold,
                fontSize = 20.sp,
                color = Color.Black
            )

            Text(
                text = description,
                style = HomeNTheme.typography.suitRegular,
                color = Color.Black,
                fontSize = 12.sp
            )
        }

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun ChoreManageEmptyScreenPreview() {
    HomeNTheme {
        ChoreManageEmptyScreen(
            uiState = ChoreManageContract.State(),
            onTooltipClick = {},
            onSelectOption = {},
            onNextButtonClick = {}
        )
    }
}