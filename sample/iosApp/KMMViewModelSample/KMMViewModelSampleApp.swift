//
//  KMMViewModelSampleApp.swift
//  KMMViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI
import KMMViewModelSavedState
import KMMViewModelSwiftUI

@main
struct KMMViewModelSampleApp: App {
    
    var body: some Scene {
        WindowGroup {
            NavigationStack {
                RootView()
            }.savedStateManager()
        }
    }
}

struct RootView: View {
    
    @SceneStorage("navigated") var navigated: Bool = false
    
    var body: some View {
        Button("GO!") {
            navigated = true
        }.navigationDestination(isPresented: $navigated) {
            ContentView()
        }
    }
}
