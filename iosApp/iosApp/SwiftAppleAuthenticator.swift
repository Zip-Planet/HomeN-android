import Foundation
import AuthenticationServices
import ComposeApp

class SwiftAppleAuthenticator: NSObject, SocialAuthenticator {
    private var continuation: CheckedContinuation<SocialAuthResult, Never>?

    func authenticate() async throws -> SocialAuthResult {
        return await withCheckedContinuation { continuation in
            self.continuation = continuation
            
            let appleIDProvider = ASAuthorizationAppleIDProvider()
            let request = appleIDProvider.createRequest()
            request.requestedScopes = [.fullName, .email]
            
            let authorizationController = ASAuthorizationController(authorizationRequests: [request])
            authorizationController.delegate = self
            authorizationController.presentationContextProvider = self
            authorizationController.performRequests()
        }
    }
}

// 애플 로그인 결과 콜백 처리
extension SwiftAppleAuthenticator: ASAuthorizationControllerDelegate {
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        if let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential {
            // 서버 검증에 필요한 identityToken 추출
            if let identityToken = appleIDCredential.identityToken,
               let tokenString = String(data: identityToken, encoding: .utf8) {
                
                let user = AppleUser(idToken: tokenString)
                continuation?.resume(returning: SocialAuthResultSuccess(data: user))
            } else {
                continuation?.resume(returning: SocialAuthResultError())
            }
        }
    }

    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        // 사용자가 취소했거나 에러가 발생한 경우
        continuation?.resume(returning: SocialAuthResultError())
    }
}

// 로그인 창을 띄울 윈도우 설정
extension SwiftAppleAuthenticator: ASAuthorizationControllerPresentationContextProviding {
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        return scene?.windows.first { $0.isKeyWindow } ?? UIWindow()
    }
}
