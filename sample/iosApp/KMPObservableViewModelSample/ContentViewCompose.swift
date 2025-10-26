//
//  ContentViewCompose.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 23/06/2024.
//

import SwiftUI
import KMPObservableViewModelSampleShared

struct ContentViewCompose: View {
    
    var body: some View {
        ComposeViewController()
            .navigationTitle(Text("Compose Multiplatform"))
            .navigationBarTitleDisplayMode(.inline)
    }
}

private struct ComposeViewController: UIViewControllerRepresentable {
    
    @StateObject var viewModel = TimeTravelViewModel()
    
    func makeUIViewController(context: Context) -> some UIViewController {
        TimeTravelViewControllerKt.TimeTravelViewController(viewModel: viewModel)
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        
    }
}
