import Foundation
import KakaoSDKUser
import KakaoSDKAuth
import KakaoSDKCommon
import ComposeApp
import AuthenticationServices

class SwiftKakaoAuthenticator: NSObject, SocialAuthenticator, ASWebAuthenticationPresentationContextProviding {
    
    func authenticate() async throws -> SocialAuthResult {
        return await withCheckedContinuation { continuation in
            
            // 1. 인가 코드 요청을 위한 파라미터 설정
            var parameters = [String: Any]()
            parameters["client_id"] = try? KakaoSDK.shared.appKey()
            parameters["redirect_uri"] = KakaoSDK.shared.redirectUri()
            parameters["response_type"] = "code"
            
            // 2. AuthApi.shared.authorizeRequest를 사용하여 URL 생성
            guard let urlRequest = AuthApi.shared.authorizeRequest(parameters: parameters),
                  let url = urlRequest.url else {
                continuation.resume(returning: SocialAuthResultError())
                return
            }
            
            // 3. ASWebAuthenticationSession으로 브라우저 실행
            // callbackURLScheme은 Info.plist에 설정된 kakao{APP_KEY} 형식이어야 합니다.
            let appKey = try! KakaoSDK.shared.appKey()
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
                
                // 4. 리다이렉트된 URL에서 "code" (인가 코드) 추출
                if let callbackURL = callbackURL,
                   let components = URLComponents(url: callbackURL, resolvingAgainstBaseURL: false),
                   let code = components.queryItems?.first(where: { $0.name == "code" })?.value {
                    
                    // 안드로이드와 동일하게 KakaoUser의 accessToken 필드에 인가 코드를 담음
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
    
    // ASWebAuthenticationPresentationContextProviding 구현
    func presentationAnchor(for session: ASWebAuthenticationSession) -> ASPresentationAnchor {
        return UIApplication.shared.windows.first { $0.isKeyWindow } ?? ASPresentationAnchor()
    }
}
