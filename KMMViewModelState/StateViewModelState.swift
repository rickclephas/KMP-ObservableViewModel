//
//  StateViewModelState.swift
//  KMMViewModelState
//
//  Created by Rick Clephas on 26/05/2023.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelSwiftUI

/// A `StateObject` property wrapper for `ViewModelState`s.
@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
@propertyWrapper
public struct StateViewModelState<ViewModel: KMMViewModel, State: AnyObject>: DynamicProperty {
    
    /// The observed `ViewModelState`.
    @StateViewModel public var wrappedValue: ViewModelState<ViewModel, State>
    
    /// The state that is being observed.
    public var projectedValue: State { wrappedValue.viewModel[keyPath: wrappedValue.stateKeyPath] }
    
    /// The `KMMViewModel` referenced by the `ViewModelState`.
    public var viewModel: ViewModel { wrappedValue.viewModel }
    
    /// Creates a `StateViewModelState` for the specified `ViewModelState`.
    /// - Parameter wrappedValue: The `ViewModelState` to observe.
    public init(wrappedValue: @autoclosure @escaping () -> ViewModelState<ViewModel, State>) {
        self._wrappedValue = StateViewModel(wrappedValue: wrappedValue())
    }
}

@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
public extension StateViewModelState {
    /// Creates a `StateViewModelState` for the specified state property.
    /// - Parameter wrappedValue: The `KMMViewModel` containing the state.
    /// - Parameter stateKeyPath: The `KeyPath` to the state property of the viewmodel.
    init(wrappedValue: ViewModel, _ stateKeyPath: KeyPath<ViewModel, State>) {
        self.init(wrappedValue: ViewModelState(wrappedValue, stateKeyPath))
    }
}
