package com.devndev.homen.ui.main.reward.main

import androidx.compose.runtime.Composable
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardContract
import com.devndev.homen.ui.theme.HomeNTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RewardExistScreen(
    uiState: RewardContract.State,
) {

}

@Preview
@Composable
fun RewardExistScreenPreview() {
    HomeNTheme {
        RewardExistScreen(
            uiState = RewardContract.State()
        )
    }
}