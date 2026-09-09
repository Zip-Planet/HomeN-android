package com.devndev.homen.ui.main.board.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.exchange_floating_btn_icon
import homen.composeapp.generated.resources.help_floating_btn_icon
import homen.composeapp.generated.resources.pen_floating_btn_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun BoardFloatingActionButton(
    onExchangeClick: () -> Unit,
    onHelpClick: () -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    // 메인 화면에 항상 떠 있는 펜 버튼
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(Res.drawable.pen_floating_btn_icon),
            contentDescription = "메뉴 열기",
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding(), end = 20.dp)
                .size(50.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { isExpanded = true }
        )
    }

    if (isExpanded) {
        var showMenu by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            showMenu = true
        }

        Dialog(
            onDismissRequest = {
                showMenu = false
                isExpanded = false
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showMenu = false
                        isExpanded = false
                    }
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = paddingValues.calculateBottomPadding(), end = 20.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    AnimatedVisibility(
                        visible = showMenu,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(durationMillis = 300)
                        ) + fadeIn(),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(durationMillis = 300)
                        ) + fadeOut()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(13.dp)
                        ) {
                            // 도움 요청
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onHelpClick()
                                    isExpanded = false
                                }
                            ) {
                                Text(
                                    text = "도움 요청",
                                    style = HomeNTheme.typography.suitSemiBold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Image(
                                    painter = painterResource(Res.drawable.help_floating_btn_icon),
                                    contentDescription = "도움 요청",
                                    modifier = Modifier.size(45.dp)
                                )
                            }

                            // 교환 요청
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onExchangeClick()
                                    isExpanded = false
                                }
                            ) {
                                Text(
                                    text = "교환 요청",
                                    style = HomeNTheme.typography.suitSemiBold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                                Image(
                                    painter = painterResource(Res.drawable.exchange_floating_btn_icon),
                                    contentDescription = "교환 요청",
                                    modifier = Modifier.size(45.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(13.dp))

                    // Dialog 안에서도 같은 위치에 펜 버튼을 배치 (닫기 버튼 역할)
                    // pen 영역이 투명 처리라서 표시시 dim 처리 된것같아 보임
                    Image(
                        painter = painterResource(Res.drawable.pen_floating_btn_icon),
                        contentDescription = "메뉴 닫기",
                        modifier = Modifier
                            .size(50.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                showMenu = false
                                isExpanded = false
                            }
                    )
                }
            }
        }
    }
}
