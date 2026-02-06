package com.devndev.homen.ui.intro.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SplashScreen(onCheckToken: (isValid: Boolean) -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        // 랜덤으로 토큰 유효성 결정
        val isValidToken = Random.nextBoolean()
        onCheckToken(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Splash Screen", style = MaterialTheme.typography.headlineLarge)
    }
}
