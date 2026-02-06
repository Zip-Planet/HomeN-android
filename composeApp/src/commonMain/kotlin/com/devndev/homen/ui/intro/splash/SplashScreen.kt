package com.devndev.homen.ui.intro.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.app_logo
import homen.composeapp.generated.resources.homen_logo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

@Composable
fun SplashScreen(onCheckToken: (isValid: Boolean) -> Unit) {
    LaunchedEffect(Unit) {
        delay(1000)
        // 랜덤으로 토큰 유효성 결정
        val isValidToken = Random.nextBoolean()
        onCheckToken(false)
    }
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

        }
    }
}
