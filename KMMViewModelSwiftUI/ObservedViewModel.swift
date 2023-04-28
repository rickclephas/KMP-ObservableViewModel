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
public struct ObservedViewModel<ViewModel: KMMViewModel>: DynamicProperty {
    
    @ObservedObject private var observableObject: ObservableViewModel<ViewModel>
    
    /// A projection of the observed `KMMViewModel` that creates bindings to its properties using dynamic member lookup.
    public var projectedValue: ObservableViewModel<ViewModel>.Projection
    
    /// The underlying `KMMViewModel` referenced by the `ObservedViewModel`.
    public var wrappedValue: ViewModel {
        get { observableObject.viewModel }
        set {
            let observableObject = observableViewModel(for: newValue)
            self.observableObject = observableObject
            self.projectedValue = ObservableViewModel.Projection(observableObject)
        }
    }
    
    @available(*, deprecated)
    public init(_ projectedValue: ObservableViewModel<ViewModel>.Projection) {
        self.observableObject = projectedValue.observableObject
        self.projectedValue = projectedValue
    }
    
    /// Creates an `ObservedViewModel` for the specified `KMMViewModel`.
    /// - Parameter wrappedValue: The `KMMViewModel` to observe.
    public init(wrappedValue: ViewModel) {
        let observableObject = observableViewModel(for: wrappedValue)
        self.observableObject = observableObject
        self.projectedValue = ObservableViewModel.Projection(observableObject)
    }
}
