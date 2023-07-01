//
//  KMMViewModelSampleApp.swift
//  KMMViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI

@main
struct KMMViewModelSampleApp: App {
    
    var body: some Scene {
        WindowGroup {
            NavigationStack {
                NavigationLink("GO!", destination: RootView())
            }
        }
    }
}
