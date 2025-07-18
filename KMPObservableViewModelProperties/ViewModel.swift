//
//  ViewModel.swift
//  KMPObservableViewModelProperties
//
//  Created by Rick Clephas on 11/06/2025.
//

import KMPObservableViewModelCore

/// A Kotlin Multiplatform ViewModel.
@dynamicMemberLookup
public protocol ViewModel: KMPObservableViewModelCore.ViewModel { }

private extension ViewModel {
    subscript<T>(dynamicMember keyPath: KeyPath<Properties, T>) -> T {
        fatalError("ViewModel subscripts will be added by @ObservableViewModel")
    }
}
