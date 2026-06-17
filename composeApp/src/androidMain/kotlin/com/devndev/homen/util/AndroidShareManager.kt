package com.devndev.homen.util

import android.content.Context
import android.content.Intent
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.TextTemplate

class AndroidShareManager(private val context: Context) : ShareManager {
    override fun shareText(text: String, homeName: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "[$homeName] 홈 초대코드: $text")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "공유하기")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    // TODO 앱 미설치 시 랜딩 페이지 추가, webUrl mobileWebUrl
    override fun shareKakaoInvite(inviteCode: String, homeName: String) {
        val textTemplate = TextTemplate(
            text = "[$homeName] 홈 초대코드: $inviteCode",
            link = Link(
                androidExecutionParams = mapOf("inviteCode" to inviteCode),
                iosExecutionParams = mapOf("inviteCode" to inviteCode)
            ),
            buttons = listOf(
                Button(
                    "앱으로 이동",
                    Link(
                        androidExecutionParams = mapOf("inviteCode" to inviteCode),
                        iosExecutionParams = mapOf("inviteCode" to inviteCode)
                    )
                )
            )
        )

        // 카카오톡 설치 여부 확인
        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            // 카카오톡으로 공유
            ShareClient.instance.shareDefault(context, textTemplate) { sharingResult, error ->
                if (error != null) {
                    // 에러 발생 시 일반 공유로 대체
                    shareText("[$homeName] 홈 초대코드: $inviteCode")
                } else if (sharingResult != null) {
                    context.startActivity(sharingResult.intent)
                }
            }
        } else {
            // 카카오톡 미설치 시 웹 공유 사용
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(textTemplate)
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: Exception) {
                // 웹 브라우저도 실패 시 일반 공유로 대체
                shareText("[$homeName] 홈 초대코드: $inviteCode")
            }
        }
    }
}
