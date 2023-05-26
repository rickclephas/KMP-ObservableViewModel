//
//  EnvironmentViewModelState.swift
//  KMMViewModelState
//
//  Created by Rick Clephas on 26/05/2023.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelSwiftUI

/// An `EnvironmentObject` property wrapper for `ViewModelState`s.
@propertyWrapper
public struct EnvironmentViewModelState<ViewModel: KMMViewModel, State: AnyObject>: DynamicProperty {
    
    /// The observed `ViewModelState`.
    @EnvironmentViewModel public var wrappedValue: ViewModelState<ViewModel, State>
    
    /// The `KMMViewModel` referenced by the `ViewModelState`.
    public var projectedValue: ViewModel { wrappedValue.viewModel }
    
    /// Creates an `EnvironmentViewModelState`.
    public init() { }
}

public extension View {
    /// Supplies a `ViewModelState` to a view subhierarchy.
    /// - Parameter wrappedValue: The `KMMViewModel` containing the state.
    /// - Parameter stateKeyPath: The `KeyPath` to the state property of the viewmodel.
    func environmentViewModelState<ViewModel: KMMViewModel, State: AnyObject>(
        _ viewModel: ViewModel,
        _ stateKeyPath: KeyPath<ViewModel, State>
    ) -> some View {
        environmentObject(ViewModelState(viewModel, stateKeyPath))
    }
}
