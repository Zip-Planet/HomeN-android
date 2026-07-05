package com.devndev.homen.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.copy_icon
import homen.composeapp.generated.resources.invite_popup_msg
import homen.composeapp.generated.resources.invite_popup_title
import homen.composeapp.generated.resources.kakao_icon
import homen.composeapp.generated.resources.share_icon
import homen.composeapp.generated.resources.x_btn
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InvitePopup(
    homeName: String,
    inviteCode: String,
    onClose: () -> Unit,
    onCopy: () -> Unit,
    onKakaoShare: () -> Unit,
    onGeneralShare: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundGray, RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(15.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onClose()
                    },
                painter = painterResource(Res.drawable.x_btn),
                contentDescription = null,
                tint = Color.Black
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 54.dp, bottom = 34.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.invite_popup_title).replace("s", homeName),
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = stringResource(Res.string.invite_popup_msg),
                    style = HomeNTheme.typography.suitMedium,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)) // 1. 클릭 영역을 둥글게 자름
                        .background(Color.White) // background에도 shape를 줄 수 있지만 clip이 우선함
                        .clickable {
                            onCopy()
                        }
                        .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = inviteCode,
                        style = HomeNTheme.typography.suitExtraBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Icon(
                        painter = painterResource(Res.drawable.copy_icon),
                        contentDescription = "복사",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 하단 공유 버튼들
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onKakaoShare() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.kakao_icon),
                            contentDescription = "공유",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Black
                        )                    }

                    // 일반 공유
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onGeneralShare() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.share_icon),
                            contentDescription = "공유",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InvitePopupPreview() {
    HomeNTheme {
        InvitePopup(
            homeName = "골든빌401",
            inviteCode = "ABC123",
            onClose = {},
            onCopy = {},
            onKakaoShare = {},
            onGeneralShare = {}
        )
    }
}