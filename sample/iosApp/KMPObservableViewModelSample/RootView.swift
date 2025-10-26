//
//  RootView.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 26/10/2025.
//

import SwiftUI

struct RootView: View {
    
    var body: some View {
        List {
            Section(header: Text("SwiftUI")) {
                NavigationLink {
                    ContentViewSwiftUIPublished()
                } label: {
                    Text("Published")
                }
                NavigationLink {
                    ContentViewSwiftUIPublishedObservable()
                } label: {
                    Text("Published + Observable")
                }
                if #available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *) {
                    NavigationLink {
                        ContentViewSwiftUIObservable()
                    } label: {
                        Text("Observable")
                    }
                }
            }
            NavigationLink {
                ContentViewCompose()
            } label: {
                Text("Compose Multiplatform")
            }
        }
        .navigationTitle(Text("KMP-ObservableViewModel"))
        .navigationBarTitleDisplayMode(.inline)
    }
}
