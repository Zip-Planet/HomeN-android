package com.devndev.homen.ui.intro.login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.getPlatform
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(onNavigateToMain: () -> Unit) {

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

    // 모든 단어의 등장이 끝난 후(isAllWordsUp이 true가 되면) 간격을 4dp(스페이스바 한 칸 정도)로 벌림
    val horizontalSpacing by animateDpAsState(
        targetValue = if (isAllWordsUp) 4.dp else 0.dp,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
    )

    HomeNScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    horizontalArrangement = Arrangement.spacedBy(horizontalSpacing, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    words.forEachIndexed { index, word ->
                        val wordAlpha = remember { Animatable(0f) }
                        val wordOffset = remember { Animatable(30f) }

                        LaunchedEffect(animProgress.value) {
                            if (animProgress.value > 0f) {
                                // 단어별 시차(Delay)를 주며 위로 이동
                                delay(index * 150L)
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

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(Res.string.terms_of_service),
                    style = HomeNTheme.typography.suitLight.copy(textDecoration = TextDecoration.Underline),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Text(text = "·", style = HomeNTheme.typography.suitLight, fontSize = 12.sp, color = Color.Black)
                Text(
                    text = stringResource(Res.string.privacy_policy),
                    style = HomeNTheme.typography.suitLight.copy(textDecoration = TextDecoration.Underline),
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Image(
                painter = painterResource(Res.drawable.kako_login_btn),
                contentDescription = "Kakao Login",
                modifier = Modifier
                    .size(width = 326.dp, height = 52.dp)
                    .clickable { onNavigateToMain() }
            )

            if (getPlatform().name.contains("iOS", ignoreCase = true)) {
                Spacer(modifier = Modifier.height(9.dp))
                Image(
                    painter = painterResource(Res.drawable.apple_login_btn),
                    contentDescription = "Apple Login",
                    modifier = Modifier
                        .size(width = 326.dp, height = 52.dp)
                        .clickable { onNavigateToMain() }
                )
            }

            Spacer(modifier = Modifier.height(31.dp))
        }
    }
}