import Foundation
import ComposeApp
import FirebaseMessaging

class SwiftFcmTokenProvider: NSObject, FcmTokenProvider {
    
    func getFcmToken() async throws -> String? {
        print("SHKIM_IOS: Fetching FCM token...")
        
        // Retry loop for APNs token assignment (up to 3 times, with 1 second delay)
        for attempt in 1...3 {
            if let token = await fetchTokenOnce() {
                return token
            }
            if attempt < 3 {
                print("SHKIM_IOS: Retrying FCM token fetch (attempt \(attempt))...")
                try? await Task.sleep(nanoseconds: 1_000_000_000) // 1 second
            }
        }
        return nil
    }
    
    private func fetchTokenOnce() async -> String? {
        return await withCheckedContinuation { continuation in
            Messaging.messaging().token { token, error in
                if let error = error {
                    print("SHKIM_IOS: Error fetching FCM registration token: \(error.localizedDescription)")
                    continuation.resume(returning: nil)
                } else if let token = token {
                    print("SHKIM_IOS: FCM token success: \(token)")
                    continuation.resume(returning: token)
                } else {
                    print("SHKIM_IOS: FCM token is nil")
                    continuation.resume(returning: nil)
                }
            }
        }
    }
    
    func getPlatform() -> String {
        return "ios"
    }
}
