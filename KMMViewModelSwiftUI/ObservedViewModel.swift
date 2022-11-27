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
    public lazy var projectedValue: ObservableViewModel<ViewModel>.Projection = {
        ObservableViewModel.Projection(observableObject)
    }()
    
    internal init(observableObject: ObservableViewModel<ViewModel>) {
        self.observableObject = observableObject
    }
    
    /// Creates an `ObservedViewModel` for the specified `KMMViewModel`.
    /// - Parameters:
    ///     - wrappedValue: The `KMMViewModel` to observe.
    ///     - keyPath: The key path to the `ViewModelScope` property of the ViewModel.
    public init(wrappedValue: ViewModel, _ keyPath: KeyPath<ViewModel, ViewModelScope>) {
        self.init(observableObject: createObservableViewModel(for: wrappedValue, with: keyPath))
    }
}

public extension ObservedViewModel {
    /// Creates an `ObservedViewModel` for the specified `KMMViewModel` projection.
    init(wrappedValue: ObservableViewModel<ViewModel>.Projection) {
        self.init(observableObject: wrappedValue.observableObject)
    }
}
