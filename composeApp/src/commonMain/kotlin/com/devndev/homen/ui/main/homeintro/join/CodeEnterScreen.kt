package com.devndev.homen.ui.main.homeintro.join

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.homeintro.join.viewmodel.CodeEnterContract
import com.devndev.homen.ui.main.homeintro.join.viewmodel.CodeEnterViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home_entry_title
import homen.composeapp.generated.resources.join_code_msg
import homen.composeapp.generated.resources.join_code_title
import homen.composeapp.generated.resources.join_code_tooltip_msg1
import homen.composeapp.generated.resources.join_code_tooltip_msg2
import homen.composeapp.generated.resources.join_code_tooltip_title
import homen.composeapp.generated.resources.next_button
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CodeEnterScreen(
    onNavToConfirm: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CodeEnterViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CodeEnterContract.Effect.NavigateToMain -> onNavToConfirm()
                is CodeEnterContract.Effect.PopBackStack -> onBackClick()
            }
        }
    }
    // TODO 유효성 검증 실패 화면 추가
    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_entry_title),
                onBackClick = { viewModel.setEvent(CodeEnterContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = HomeNTheme.dimensions.topPadding,
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding
                )
        ) {
            if (uiState.showTooltip) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures {
                                viewModel.setEvent(CodeEnterContract.Event.OnTooltipToggle(false))
                            }
                        }
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.join_code_title),
                    style = HomeNTheme.typography.suitBold.copy(lineHeight = 1.6.em),
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(13.dp))
                Text(
                    text = stringResource(Res.string.join_code_msg),
                    style = HomeNTheme.typography.suitRegular.copy(lineHeight = 1.6.em),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(20.dp))
                CodeInputSection(
                    code = uiState.code,
                    onCodeChanged = {
                        viewModel.setEvent(CodeEnterContract.Event.OnCodeChanged(it))
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                HomeNButton(
                    text = stringResource(Res.string.next_button),
                    onClick = { viewModel.setEvent(CodeEnterContract.Event.OnJoinClick) },
                    enabled = uiState.code.length == 6
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 1.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TooltipButton {
                    viewModel.setEvent(CodeEnterContract.Event.OnTooltipToggle(!uiState.showTooltip))
                }

                if (uiState.showTooltip) {
                    HomeNTooltip(
                        title = stringResource(Res.string.join_code_tooltip_title),
                        messages = listOf(
                            stringResource(Res.string.join_code_tooltip_msg1),
                            stringResource(Res.string.join_code_tooltip_msg2)
                        ),
                        onCloseClick = { viewModel.setEvent(CodeEnterContract.Event.OnTooltipToggle(false)) },
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures { /* 툴팁 내부 클릭 시 이벤트 소비하여 닫힘 방지 */ }
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun CodeInputSection(
    code: String,
    onCodeChanged: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = code,
            onValueChange = {
                if (it.length <= 6) onCodeChanged(it.uppercase())
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(6) { index ->
                        val char = code.getOrNull(index)?.toString() ?: ""

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(Color.White, shape = RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = char,
                                style = HomeNTheme.typography.suitSemiBold,
                                fontSize = 24.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        )
    }
}
