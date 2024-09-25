//
//  ComposeView.swift
//  iosApp
//
//  Created by Thanapol Sopanhari on 29/10/2566 BE.
//  Copyright © 2566 BE orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct ComposeView: UIViewControllerRepresentable {

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}

    func makeUIViewController(context: Context) -> some UIViewController {
        MainViewControllerKt.MainViewController()
    }
}
