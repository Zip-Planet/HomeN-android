package com.devndev.homen.util

interface ShareManager {
    fun shareText(text: String, homeName: String)
    fun shareKakaoInvite(inviteCode: String, homeName: String)
}
