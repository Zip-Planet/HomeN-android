import Foundation
import KakaoSDKUser
import KakaoSDKAuth
import KakaoSDKCommon
import ComposeApp
import AuthenticationServices

class SwiftKakaoAuthenticator: NSObject, SocialAuthenticator, ASWebAuthenticationPresentationContextProviding {
    
    func authenticate() async throws -> SocialAuthResult {
        return await withCheckedContinuation { continuation in
            
            // 1. 인스턴스(shared)를 통해 함수() 호출
            let appKey = (try? KakaoSDK.shared.appKey()) ?? ""
            let redirectUri = KakaoSDK.shared.redirectUri()
            let kaHeader = KakaoSDK.shared.kaHeader() // 인스턴스 메서드로 호출
            
            // 2. 인가 코드 요청 URL 생성
            // KOE033 에러 해결을 위해 'ka' 파라미터를 쿼리에 직접 포함
            var urlComponents = URLComponents(string: "https://kauth.kakao.com/oauth/authorize")!
            urlComponents.queryItems = [
                URLQueryItem(name: "client_id", value: appKey),
                URLQueryItem(name: "redirect_uri", value: redirectUri),
                URLQueryItem(name: "response_type", value: "code"),
                URLQueryItem(name: "ka", value: kaHeader) // ka 파라미터 추가
            ]
            
            guard let url = urlComponents.url else {
                continuation.resume(returning: SocialAuthResultError())
                return
            }
            
            // 3. ASWebAuthenticationSession 실행
            let session = ASWebAuthenticationSession(url: url, callbackURLScheme: "kakao\(appKey)") { callbackURL, error in
                if let error = error {
                    let nsError = error as NSError
                    if nsError.domain == ASWebAuthenticationSessionErrorDomain &&
                       nsError.code == ASWebAuthenticationSessionError.canceledLogin.rawValue {
                        continuation.resume(returning: SocialAuthResultUserCancelled())
                    } else {
                        continuation.resume(returning: SocialAuthResultError())
                    }
                    return
                }
                
                // 4. 인가 코드 추출
                if let callbackURL = callbackURL,
                   let components = URLComponents(url: callbackURL, resolvingAgainstBaseURL: false),
                   let code = components.queryItems?.first(where: { $0.name == "code" })?.value {
                    
                    let user = KakaoUser(accessToken: code)
                    continuation.resume(returning: SocialAuthResultSuccess(data: user))
                } else {
                    continuation.resume(returning: SocialAuthResultError())
                }
            }
            
            session.presentationContextProvider = self
            session.prefersEphemeralWebBrowserSession = false
            session.start()
        }
    }
    
    func presentationAnchor(for session: ASWebAuthenticationSession) -> ASPresentationAnchor {
        return UIApplication.shared.windows.first { $0.isKeyWindow } ?? ASPresentationAnchor()
    }
}
