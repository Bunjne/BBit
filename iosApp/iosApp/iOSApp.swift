import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        HelperKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
		GeometryReader { geo in
		    ComposeView(
		    topSafeArea: Float(geo.safeAreaInsets.top),
		    bottomSafeArea: Float(geo.safeAreaInsets.bottom)
		    )
		    .edgesIgnoringSafeArea(.all)
//                        ComposeViewController(
//                            ...,
//                            topSafeArea: Float(geo.safeAreaInsets.top),
//                            bottomSafeArea: Float(geo.safeAreaInsets.bottom)
//                         )
//                         ContentView()
//                         .edgesIgnoringSafeArea(.all)
//                     }
// 			ContentView()
		}
		}
	}
}
