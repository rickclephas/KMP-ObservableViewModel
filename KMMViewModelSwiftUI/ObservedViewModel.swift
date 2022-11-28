//
//  ObservedViewModel.swift
//  KMMViewModelSwiftUI
//
//  Created by Rick Clephas on 27/11/2022.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelCoreObjC

/// An `ObservedObject` property wrapper for `KMMViewModel`s.
@propertyWrapper
public struct ObservedViewModel<ViewModel>: DynamicProperty {
    
    @ObservedObject private var observableObject: ObservableViewModel<ViewModel>
    
    /// The underlying `KMMViewModel` referenced by the `ObservedViewModel`.
    public var wrappedValue: ViewModel { observableObject.viewModel }
    
    /// A projection of the observed `KMMViewModel` that creates bindings to its properties using dynamic member lookup.
    public var projectedValue: ObservableViewModel<ViewModel>.Projection
    
    /// Creates an `ObservedViewModel` for the specified `KMMViewModel` projection.
    public init(_ projectedValue: ObservableViewModel<ViewModel>.Projection) {
        self.observableObject = projectedValue.observableObject
        self.projectedValue = projectedValue
    }
    
    /// Creates an `ObservedViewModel` for the specified `KMMViewModel`.
    /// - Parameters:
    ///     - wrappedValue: The `KMMViewModel` to observe.
    ///     - keyPath: The key path to the `ViewModelScope` property of the ViewModel.
    public init(wrappedValue: ViewModel, _ keyPath: KeyPath<ViewModel, ViewModelScope>) {
        let observableObject = createObservableViewModel(for: wrappedValue, with: keyPath)
        self.observableObject = observableObject
        self.projectedValue = ObservableViewModel.Projection(observableObject)
    }
}
