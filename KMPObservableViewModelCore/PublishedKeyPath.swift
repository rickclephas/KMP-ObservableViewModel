//
//  PublishedKeyPath.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 09/06/2025.
//

import KMPObservableViewModelCoreObjC

/// A published `KeyPath` which uses an `ObservableObject` to emit change events.
public final class PublishedKeyPath: ViewModelKeyPath {
    
    public static let shared = PublishedKeyPath()
    
    private init() { }
    
    public func access(_ publisher: any Publisher) {
        // Published keyPaths only emit on willSet
    }
    
    public func willSet(_ publisher: any Publisher) {
        publisher.cast().send()
    }
    
    public func didSet(_ publisher: any Publisher) {
        // Published keyPaths only emit on willSet
    }
}
