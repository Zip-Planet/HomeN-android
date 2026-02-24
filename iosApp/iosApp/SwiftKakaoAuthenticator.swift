import Foundation
import KakaoSDKUser
import KakaoSDKAuth
import KakaoSDKCommon
import ComposeApp

class SwiftKakaoAuthenticator: SocialAuthenticator {
    func authenticate() async throws -> SocialAuthResult {
        return await withCheckedContinuation { continuation in
            let completion: (OAuthToken?, Error?) -> Void = { (token, error) in
                if let error = error {
                    continuation.resume(returning: SocialAuthResultError())
                } else if let token = token {
                    let user = KakaoUser(accessToken: token.accessToken)
                    continuation.resume(returning: SocialAuthResultSuccess(data: user))
                }
            }

            // 카카오톡 앱 설치 여부에 따라 로그인 방식 결정
            if UserApi.isKakaoTalkLoginAvailable() {
                UserApi.shared.loginWithKakaoTalk(completion: completion)
            } else {
                UserApi.shared.loginWithKakaoAccount(completion: completion)
            }
        }
    }
}
