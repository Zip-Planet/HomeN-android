package com.devndev.homen.ui.main.home.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNLongTextField
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.home.memo.viewModel.MemoContract
import com.devndev.homen.ui.main.home.memo.viewModel.MemoViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.create_memo_hint
import homen.composeapp.generated.resources.create_memo_title
import homen.composeapp.generated.resources.edit_memo_title
import homen.composeapp.generated.resources.memo_description
import homen.composeapp.generated.resources.memo_title
import homen.composeapp.generated.resources.save_button
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemoScreen(
    viewModel: MemoViewModel = koinViewModel(),
    choreId: Int,
    isEdit: Boolean,
    memoId: Int?,
    content: String?,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                MemoContract.Effect.NavigateToBack -> {
                    onBackClick()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (isEdit) {
            viewModel.setEvent(MemoContract.Event.OnInitEdit(content!!))
        }
    }

    MemoContent(
        uiState = uiState,
        isEdit = isEdit,
        onValueChange = {
            viewModel.setEvent(MemoContract.Event.OnValueChange(it))
        },
        onSaveClick = {
            viewModel.setEvent(MemoContract.Event.OnSaveClick(isEdit, choreId, memoId))
        },
        onBackClick = {
            viewModel.setEvent(MemoContract.Event.OnBackClick)
        }
    )

}

@Composable
fun MemoContent(
    uiState: MemoContract.State,
    isEdit: Boolean,
    onSaveClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val title = if (isEdit) stringResource(Res.string.edit_memo_title) else stringResource(Res.string.create_memo_title)
    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = title,
                onBackClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                .padding(bottom = HomeNTheme.dimensions.bottomPadding)
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = stringResource(Res.string.memo_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(Res.string.memo_description),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeNLongTextField(
                value = uiState.content,
                onValueChange = {
                    onValueChange(it)
                },
                hint = stringResource(Res.string.create_memo_hint),
                maxChar = 0,
                enabled = true,
                regex = null,
                height = 116
            )
            Spacer(modifier = Modifier.weight(1f))

            HomeNButton(
                text = stringResource(Res.string.save_button),
                onClick = {
                    onSaveClick()
                },
                enabled = uiState.content.isNotEmpty()
            )
        }
    }
}

@Preview
@Composable
fun MemoContentPreview() {
    MemoContent(
        uiState = MemoContract.State(),
        isEdit = false,
        onValueChange = {},
        onSaveClick = {},
        onBackClick = {}
    )
}