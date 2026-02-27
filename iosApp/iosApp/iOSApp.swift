import SwiftUI
import ComposeApp
import KakaoSDKCommon
import KakaoSDKAuth

@main
struct iOSApp: App {
    init() {
        // 1. Info.plist (XCConfig)에서 카카오 앱 키 읽기
        let kakaoAppKey = Bundle.main.object(forInfoDictionaryKey: "KakaoAppKey") as? String ?? ""
        
        // 2. 카카오 SDK 초기화
        KakaoSDK.initSDK(appKey: kakaoAppKey)
        
        // 3. Koin 초기화 및 Swift 구현체들 주입
        // 이제 카카오와 애플 두 가지 구현체를 모두 주입합니다.
        KoinInitializerKt.doInitKoin(
            kakaoAuthenticator: SwiftKakaoAuthenticator(),
            appleAuthenticator: SwiftAppleAuthenticator()
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
                }
        }
    }
}
