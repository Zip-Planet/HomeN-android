package com.devndev.homen.ui.intro.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.app_logo
import homen.composeapp.generated.resources.homen_logo
import homen.composeapp.generated.resources.login_screen_msg
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreen(onCheckToken: (isValid: Boolean) -> Unit) {
    val animatedHeight = remember { Animatable(0f) }
    val animProgress = remember { Animatable(0f) } // 글자 애니메이션 제어용

    val loginMsg = stringResource(Res.string.login_screen_msg)
    val words = remember { loginMsg.split(" ") }

    // 모든 단어가 위로 올라왔는지 확인하는 상태 (간격 벌리기 트리거)
    var isAllWordsUp by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(700)
        // 1. 수직선 애니메이션 실행
        animatedHeight.animateTo(
            targetValue = 74f,
            animationSpec = tween(durationMillis = 500)
        )

        // 2. 글자 애니메이션 시작 트리거
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 200)
        )
    }

    val horizontalSpacing by animateDpAsState(
        targetValue = if (isAllWordsUp) 3.dp else 0.dp,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(isAllWordsUp) {
        if (isAllWordsUp) {
            delay(1000)
            onCheckToken(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 210.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.homen_logo),
            contentDescription = "HomeN Logo",
            modifier = Modifier.height(85.dp).width(75.dp)
        )

        Spacer(modifier = Modifier.height(9.dp))

        Text(
            text = stringResource(Res.string.app_logo),
            style = HomeNTheme.typography.suitHeavy,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(23.dp))

        // 수직선
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(animatedHeight.value.dp)
                .background(color = MaterialTheme.colorScheme.onBackground)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    horizontalSpacing,
                    Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                words.forEachIndexed { index, word ->
                    val wordAlpha = remember { Animatable(0f) }
                    val wordOffset = remember { Animatable(30f) }

                    LaunchedEffect(animProgress.value) {
                        if (animProgress.value > 0f) {
                            // 단어별 시차(Delay)를 주며 위로 이동
                            delay(index * 100L)
                            launch {
                                wordAlpha.animateTo(1f, tween(400))
                            }
                            launch {
                                wordOffset.animateTo(0f, tween(400, easing = EaseOutBack))
                            }

                            // 마지막 단어까지 올라왔다면 간격 벌리기 시작
                            if (index == words.size - 1) {
                                delay(400) // 마지막 글자가 다 올라올 때까지 대기
                                isAllWordsUp = true
                            }
                        }
                    }

                    Text(
                        text = word,
                        style = HomeNTheme.typography.suitMedium,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = wordAlpha.value
                                translationY = wordOffset.value.dp.toPx()
                            }
                    )
                }
            }
        }
    }
}
