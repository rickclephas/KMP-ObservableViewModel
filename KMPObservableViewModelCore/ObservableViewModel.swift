//
//  ObservableViewModel.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 27/11/2022.
//

import Combine
import KMPObservableViewModelCoreObjC

/// Gets an `ObservableObject` for the specified `ViewModel`.
/// - Parameter viewModel: The `ViewModel` to wrap in an `ObservableObject`.
public func observableViewModel<VM: ViewModel>(
    for viewModel: VM
) -> ObservableViewModel<VM> {
    return ObservableViewModel(viewModel)
}

/// Gets an `ObservableObject` for the specified `ViewModel`.
/// - Parameter viewModel: The `ViewModel` to wrap in an `ObservableObject`.
public func observableViewModel<VM: ViewModel>(
    for viewModel: VM?
) -> ObservableViewModel<VM>? {
    guard let viewModel = viewModel else { return nil }
    let observableViewModel = observableViewModel(for: viewModel)
    return observableViewModel
}

/// An `ObservableObject` for a `ViewModel`.
public final class ObservableViewModel<VM: ViewModel>: ObservableObject, Hashable {
    
    public let objectWillChange: ObservableViewModelPublisher
    
    /// The observed `ViewModel`.
    public let viewModel: VM
    
    internal init(_ viewModel: VM) {
        objectWillChange = viewModel.viewModelWillChange
        self.viewModel = viewModel
    }
    
    public static func == (lhs: ObservableViewModel<VM>, rhs: ObservableViewModel<VM>) -> Bool {
        return ObjectIdentifier(lhs) == ObjectIdentifier(rhs)
    }
    
    public func hash(into hasher: inout Hasher) {
        hasher.combine(ObjectIdentifier(self))
    }
}
