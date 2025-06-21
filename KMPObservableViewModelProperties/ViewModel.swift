//
//  ViewModel.swift
//  KMPObservableViewModelProperties
//
//  Created by Rick Clephas on 11/06/2025.
//

import KMPObservableViewModelCore

/// A Kotlin Multiplatform ViewModel.
@dynamicMemberLookup
public protocol ViewModel: KMPObservableViewModelCore.ViewModel {
    
    associatedtype Properties: KMPObservableViewModelProperties.Properties
    
    /// The observable properties of `this` ViewModel.
    var __properties: Properties { get }
    
    /// Returns the value of an observable property.
    subscript<T>(dynamicMember keyPath: KeyPath<Properties, T>) -> T { get }
    
    /// Gets or sets the value of an observable property.
    subscript<T>(dynamicMember keyPath: ReferenceWritableKeyPath<Properties, T>) -> T { get set }
}
