import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        #if DEBUG
            NapierUtilKt.setUpNapier()
        #endif
    }

	var body: some Scene {
		WindowGroup {
            ContentView()
                .edgesIgnoringSafeArea(.all)
		}
	}
}
