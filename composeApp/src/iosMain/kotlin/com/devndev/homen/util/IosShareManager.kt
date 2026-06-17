package com.devndev.homen.util

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

class IosShareManager(
    private val onKakaoShare: (inviteCode: String, homeName: String) -> Unit
) : ShareManager {
    override fun shareText(text: String, homeName: String) {
        val window = UIApplication.sharedApplication.keyWindow
        val rootViewController = window?.rootViewController

        val activityViewController = UIActivityViewController(
            activityItems = listOf("[$homeName] 홈 초대코드: $text"),
            applicationActivities = null
        )

        rootViewController?.presentViewController(activityViewController, animated = true, completion = null)
    }

    override fun shareKakaoInvite(inviteCode: String, homeName: String) {
        onKakaoShare(inviteCode, homeName)
    }
}
