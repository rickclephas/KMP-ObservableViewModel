//
//  KMPObservableViewModelSampleApp.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI

@main
struct KMPObservableViewModelSampleApp: App {
    
    var body: some Scene {
        WindowGroup {
            NavigationStack {
                NavigationLink("SwiftUI", destination: ContentView())
                Spacer().frame(height: 24)
                NavigationLink("Compose MP", destination: ContentViewMP())
            }
        }
    }
}
