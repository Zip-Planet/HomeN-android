import SwiftUI
import ComposeApp
import KakaoSDKCommon
import KakaoSDKAuth

@main
struct iOSApp: App {
    init() {
        let kakaoAppKey = Bundle.main.object(forInfoDictionaryKey: "KakaoAppKey") as? String ?? ""
        
        KakaoSDK.initSDK(appKey: kakaoAppKey)
        
        KoinInitializerKt.doInitKoin(kakaoAuthenticator: SwiftKakaoAuthenticator())
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL { url in
                    if AuthApi.isKakaoTalkLoginUrl(url) {
                        _ = AuthController.handleOpenUrl(url: url)
                    }
                }
        }
    }
}
