import SwiftUI
import ComposeApp
import KakaoSDKCommon
import KakaoSDKAuth
import KakaoSDKShare
import KakaoSDKTemplate

@main
struct iOSApp: App {
    init() {
        // 1. Info.plist (XCConfig)에서 카카오 앱 키 읽기
        let kakaoAppKey = Bundle.main.object(forInfoDictionaryKey: "KakaoAppKey") as? String ?? ""

        // 2. 카카오 SDK 초기화
        KakaoSDK.initSDK(appKey: kakaoAppKey)

        // 3. Koin 초기화 및 Swift 구현체들 주입
        KoinInitializerKt.doInitKoin(
            kakaoAuthenticator: SwiftKakaoAuthenticator(),
            appleAuthenticator: SwiftAppleAuthenticator(),
            onKakaoShare: { inviteCode, homeName in
                // TODO 앱 미설치 시 랜딩 페이지 추가, webUrl mobileWebUrl
                let template = TextTemplate(
                    text: "[\(homeName)] 홈 초대코드: \(inviteCode)",
                    link: Link(
                               androidExecutionParams: ["inviteCode": inviteCode],
                               iosExecutionParams: ["inviteCode": inviteCode]),
                    buttons: [
                        Button(title: "앱으로 이동",
                               link: Link(androidExecutionParams: ["inviteCode": inviteCode],
                                          iosExecutionParams: ["inviteCode": inviteCode]))
                    ]
                )

                if ShareApi.isKakaoTalkSharingAvailable() {
                    ShareApi.shared.shareDefault(templatable: template) { (sharingResult, error) in
                        if let error = error {
                            print("Kakao share error: \(error)")
                        } else if let sharingResult = sharingResult {
                            UIApplication.shared.open(sharingResult.url, options: [:], completionHandler: nil)
                        }
                    }
                } else {
                    if let url = ShareApi.shared.makeDefaultUrl(templatable: template) {
                        UIApplication.shared.open(url, options: [:], completionHandler: nil)
                    }
                }
            }
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                    // 4. 카카오톡 앱 로그인 리다이렉트 처리
                    if AuthApi.isKakaoTalkLoginUrl(url) {
                        _ = AuthController.handleOpenUrl(url: url)
                    }

                    // 5. 카카오톡 공유 링크(Deep Link) 진입 처리
                    if ShareApi.isKakaoTalkSharingUrl(url) {
                        print("카카오 공유 링크를 통해 앱에 진입했습니다: \(url)")
                    }
                }
        }
    }
}
