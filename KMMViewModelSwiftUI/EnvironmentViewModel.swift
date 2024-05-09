//
//  EnvironmentViewModel.swift
//  KMMViewModelSwiftUI
//
//  Created by Rick Clephas on 27/11/2022.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelCoreObjC

/// An `EnvironmentObject` property wrapper for `ViewModel`s.
@propertyWrapper
public struct EnvironmentViewModel<VM: ViewModel>: DynamicProperty {
    
    @EnvironmentObject private var observableObject: ObservableViewModel<VM>
    
    /// The underlying `ViewModel` referenced by the `EnvironmentViewModel`.
    public var wrappedValue: VM { observableObject.viewModel }
    
    /// A projection of the observed `ViewModel` that creates bindings to its properties using dynamic member lookup.
    public var projectedValue: ObservableViewModel<VM>.Projection {
        ObservableViewModel.Projection(observableObject)
    }
    
    /// Creates an `EnvironmentViewModel`.
    public init() { }
}

public extension View {
    /// Supplies a `ViewModel` to a view subhierarchy.
    /// - Parameter viewModel: The `ViewModel` to supply to a view subhierarchy.
    func environmentViewModel<VM: ViewModel>(_ viewModel: VM) -> some View {
        environmentObject(observableViewModel(for: viewModel))
    }
}
