//
//  ViewModelState.swift
//  KMMViewModelState
//
//  Created by Rick Clephas on 26/05/2023.
//

import Combine
import KMMViewModelCore
import KMMViewModelCoreObjC
import SwiftUI

/// Provides access to a state property of a `KMMViewModel` using dynamic member lookup.
@dynamicMemberLookup
public final class ViewModelState<ViewModel: KMMViewModel, State: AnyObject>: KMMViewModel {
    
    internal let viewModel: ViewModel
    internal let stateKeyPath: KeyPath<ViewModel, State>
    
    public var viewModelScope: ViewModelScope { viewModel.viewModelScope }
    public var objectWillChange: ObservableObjectPublisher { viewModel.objectWillChange }

    internal init(_ viewModel: ViewModel, _ stateKeyPath: KeyPath<ViewModel, State>) {
        self.viewModel = viewModel
        self.stateKeyPath = stateKeyPath
    }
    
    public subscript<T>(dynamicMember keyPath: KeyPath<State, T>) -> T {
        viewModel[keyPath: stateKeyPath][keyPath: keyPath]
    }
}
