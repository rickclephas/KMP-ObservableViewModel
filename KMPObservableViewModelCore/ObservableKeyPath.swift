//
//  ObservableKeyPath.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 11/06/2025.
//

import Observation
import KMPObservableViewModelCoreObjC

/// An observable `KeyPath` which uses an `ObservationRegistrar` to track changes.
@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
public final class ObservableKeyPath<Root, Value>: ViewModelKeyPath where Root: AnyObject, Root: Observable {
    
    private weak var subject: Root?
    private let keyPath: KeyPath<Root, Value>
    
    /// Creates a new `ObservableKeyPath` for the provided `subject` and `keyPath`.
    public init(_ subject: Root, _ keyPath: KeyPath<Root, Value>) {
        self.subject = subject
        self.keyPath = keyPath
    }
    
    public func access(_ publisher: any Publisher) {
        guard let subject else { return }
        publisher.cast().observationRegistrar.access(subject, keyPath: keyPath)
    }
    
    public func willSet(_ publisher: any Publisher) {
        guard let subject else { return }
        publisher.cast().observationRegistrar.willSet(subject, keyPath: keyPath)
    }
    
    public func didSet(_ publisher: any Publisher) {
        guard let subject else { return }
        publisher.cast().observationRegistrar.didSet(subject, keyPath: keyPath)
    }
}
