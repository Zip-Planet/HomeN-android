package com.devndev.homen.ui.intro.register

import androidx.compose.runtime.Composable
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.profile_setting_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    onNavBack: () -> Unit
) {
    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.profile_setting_title),
                onBackClick = onNavBack,
            )
        }
    ) {

    }
}