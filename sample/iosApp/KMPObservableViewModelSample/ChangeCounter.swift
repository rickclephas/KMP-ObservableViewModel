//
//  ChangeCounter.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 29/06/2025.
//

import SwiftUI

@MainActor
class Counter: ObservableObject {
    
    private var count = 0
    
    func incrementAndGet() -> Int {
        count += 1
        return count
    }
}

struct ChangeCounter<Content: View>: View {
    
    var count: Int
    var content: Content
    
    init(_ count: Int, @ViewBuilder _ content: () -> Content) {
        self.count = count
        self.content = content()
    }
    
    var body: some View {
        ZStack(alignment: .bottom) {
            content.padding(.bottom, 32)
            Text("Changes: \(count)")
                .foregroundStyle(.foreground.opacity(0.6))
                .dynamicTypeSize(.small)
                .padding(.horizontal, 16)
                .background(.background)
        }
        .frame(minWidth: 0, maxWidth: .infinity)
        .padding(.vertical, 16)
        .padding(.horizontal, 8)
        .background { RoundedRectangle(cornerRadius: 16).stroke(.foreground.opacity(0.3), lineWidth: 2).padding(.bottom, 24) }
        .padding(.horizontal, 8)
    }
}
