//
//  ObservedViewModel.swift
//  KMPObservableViewModelSwiftUI
//
//  Created by Rick Clephas on 27/11/2022.
//

import SwiftUI
import KMPObservableViewModelCore
import KMPObservableViewModelCoreObjC

/// An `ObservedObject` property wrapper for `ViewModel`s.
@propertyWrapper
public struct ObservedViewModel<VM: ViewModel>: DynamicProperty {
    
    @ObservedObject private var observableObject: ObservableViewModel<VM>
    
    /// A projection of the observed `ViewModel` that creates bindings to its properties using dynamic member lookup.
    public var projectedValue: ObservableViewModel<VM>.Projection
    
    /// The underlying `ViewModel` referenced by the `ObservedViewModel`.
    public var wrappedValue: VM {
        get { observableObject.viewModel }
        set {
            let observableObject = observableViewModel(for: newValue)
            self.observableObject = observableObject
            self.projectedValue = ObservableViewModel.Projection(observableObject)
        }
    }
    
    /// Creates an `ObservedViewModel` for the specified `ViewModel`.
    /// - Parameter wrappedValue: The `ViewModel` to observe.
    public init(wrappedValue: VM) {
        let observableObject = observableViewModel(for: wrappedValue)
        self.observableObject = observableObject
        self.projectedValue = ObservableViewModel.Projection(observableObject)
    }
}
