//
//  ObservedViewModelState.swift
//  KMMViewModelState
//
//  Created by Rick Clephas on 26/05/2023.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelSwiftUI

/// An `ObservedObject` property wrapper for `ViewModelState`s.
@propertyWrapper
public struct ObservedViewModelState<ViewModel: KMMViewModel, State: AnyObject>: DynamicProperty {
    
    /// The observed `ViewModelState`.
    @ObservedViewModel public var wrappedValue: ViewModelState<ViewModel, State>
    
    /// The state that is being observed.
    public var projectedValue: State { wrappedValue.viewModel[keyPath: wrappedValue.stateKeyPath] }
    
    /// The `KMMViewModel` referenced by the `ViewModelState`.
    public var viewModel: ViewModel { wrappedValue.viewModel }
    
    /// Creates an `ObservedViewModelState` for the specified `ViewModelState`.
    /// - Parameter wrappedValue: The `ViewModelState` to observe.
    public init(wrappedValue: ViewModelState<ViewModel, State>) {
        self.wrappedValue = wrappedValue
    }
}

public extension ObservedViewModelState {
    /// Creates an `ObservedViewModelState` for the specified state property.
    /// - Parameter wrappedValue: The `KMMViewModel` containing the state.
    /// - Parameter stateKeyPath: The `KeyPath` to the state property of the viewmodel.
    init(wrappedValue: ViewModel, _ stateKeyPath: KeyPath<ViewModel, State>) {
        self.init(wrappedValue: ViewModelState(wrappedValue, stateKeyPath))
    }
}
