//
//  Properties.swift
//  KMPObservableViewModelProperties
//
//  Created by Rick Clephas on 10/06/2025.
//

import Foundation
import Combine
import KMPObservableViewModelCore

/// A class that stores all `PublishedKeyPath` properties of a ViewModel.
public protocol PublishedProperties: Properties { }

public extension PublishedProperties {
    
    /// Returns the value of a property ensuring the `keyPath` is being registered.
    subscript<Value>(published keyPath: KeyPath<Self, Value>) -> Value {
        let value = self[keyPath: keyPath]
        guard shouldRegisterKeyPath(), Thread.isMainThread else { return value }
        registerKeyPath(PublishedKeyPath.shared)
        return self[keyPath: keyPath]
    }
    
    /// Gets or sets the value of a property ensuring the `keyPath` is being registered.
    subscript<Value>(published keyPath: ReferenceWritableKeyPath<Self, Value>) -> Value {
        get { self[published: keyPath as KeyPath<Self, Value>] }
        set { self[keyPath: keyPath] = newValue }
    }
    
    /// `ObservableProperties` implementation required for compilation if only `PublishedProperties` is used.
    @_disfavoredOverload
    subscript<Value>(observable keyPath: KeyPath<Self, Value>) -> Value {
        self[published: keyPath]
    }
    
    /// `ObservableProperties` implementation required for compilation if only `PublishedProperties` is used.
    @_disfavoredOverload
    subscript<Value>(observable keyPath: ReferenceWritableKeyPath<Self, Value>) -> Value {
        get { self[published: keyPath] }
        set { self[published: keyPath] = newValue }
    }
}
