package com.devndev.homen.ui.intro.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.getPlatform
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.app_logo
import homen.composeapp.generated.resources.apple_login_btn
import homen.composeapp.generated.resources.homen_logo
import homen.composeapp.generated.resources.kako_login_btn
import homen.composeapp.generated.resources.login_screen_msg
import homen.composeapp.generated.resources.privacy_policy
import homen.composeapp.generated.resources.terms_of_service
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(onNavigateToMain: () -> Unit) {
    HomeNScreen(
        containerColor = Color.White
    ) {
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
                    .height(74.dp)
                    .background(color = MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(Res.string.login_screen_msg),
                fontSize = 15.sp,
                style = HomeNTheme.typography.suitMedium,
            )

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
                Text(
                    text = "·",
                    style = HomeNTheme.typography.suitLight,
                    fontSize = 12.sp,
                    color = Color.Black
                )
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
