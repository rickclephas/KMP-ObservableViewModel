//
//  Properties.swift
//  KMPObservableViewModelProperties
//
//  Created by Rick Clephas on 10/06/2025.
//

import Foundation
import Observation
import KMPObservableViewModelCore

/// A class that stores all `ObservableKeyPath` properties of a ViewModel.
@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
public protocol ObservableProperties: Properties, Observable { }

@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
public extension ObservableProperties {
    
    /// Returns the value of a property ensuring the `keyPath` is being registered.
    subscript<Value>(keyPath: KeyPath<Self, Value>) -> Value {
        let value = self[keyPath: keyPath]
        guard shouldRegisterKeyPath(), Thread.isMainThread else { return value }
        registerKeyPath(ObservableKeyPath(self, keyPath))
        return self[keyPath: keyPath]
    }
    
    /// Gets or sets the value of a property ensuring the `keyPath` is being registered.
    subscript<Value>(keyPath: ReferenceWritableKeyPath<Self, Value>) -> Value {
        get { self[keyPath as KeyPath<Self, Value>] }
        set { self[keyPath: keyPath] = newValue }
    }
}
