//
//  StateViewModel.swift
//  KMMViewModelSwiftUI
//
//  Created by Rick Clephas on 27/11/2022.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelCoreObjC

/// A `StateObject` property wrapper for `ViewModel`s.
@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
@propertyWrapper
public struct StateViewModel<VM: ViewModel>: DynamicProperty {
    
    @StateObject private var observableObject: ObservableViewModel<VM>
    
    /// The underlying `ViewModel` referenced by the `StateViewModel`.
    public var wrappedValue: VM { observableObject.viewModel }
    
    /// A projection of the observed `ViewModel` that creates bindings to its properties using dynamic member lookup.
    public var projectedValue: ObservableViewModel<VM>.Projection {
        ObservableViewModel.Projection(observableObject)
    }
    
    /// Creates a `StateViewModel` for the specified `ViewModel`.
    /// - Parameter wrappedValue: The `ViewModel` to observe.
    public init(wrappedValue: @autoclosure @escaping () -> VM) {
        self._observableObject = StateObject(wrappedValue: observableViewModel(for: wrappedValue()))
    }
}
