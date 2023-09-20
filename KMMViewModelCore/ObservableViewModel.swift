//
//  ObservableViewModel.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 27/11/2022.
//

import Combine
import KMMViewModelCoreObjC

@available(*, deprecated, renamed: "observableViewModel")
public func createObservableViewModel<ViewModel: KMMViewModel>(
    for viewModel: ViewModel
) -> ObservableViewModel<ViewModel> {
    observableViewModel(for: viewModel)
}

/// Gets an `ObservableObject` for the specified `KMMViewModel`.
/// - Parameter viewModel: The `KMMViewModel` to wrap in an `ObservableObject`.
public func observableViewModel<ViewModel: KMMViewModel>(
    for viewModel: ViewModel
) -> ObservableViewModel<ViewModel> {
    let publishers = observableViewModelPublishers(for: viewModel)
    return ObservableViewModel(publishers, viewModel)
}

/// Gets an `ObservableObject` for the specified `KMMViewModel`.
/// - Parameter viewModel: The `KMMViewModel` to wrap in an `ObservableObject`.
public func observableViewModel<ViewModel: KMMViewModel>(
    for viewModel: ViewModel?
) -> ObservableViewModel<ViewModel>? {
    guard let viewModel = viewModel else { return nil }
    let observableViewModel = observableViewModel(for: viewModel)
    return observableViewModel
}

/// An `ObservableObject` for a `KMMViewModel`.
public final class ObservableViewModel<ViewModel: KMMViewModel>: ObservableObject, Hashable {
    
    public let objectWillChange: ObservableViewModelPublisher
    
    /// The observed `KMMViewModel`.
    public let viewModel: ViewModel
    
    /// Holds a strong reference to the publishers
    private let publishers: ObservableViewModelPublishers
    
    internal init(_ publishers: ObservableViewModelPublishers, _ viewModel: ViewModel) {
        objectWillChange = publishers.publisher
        self.viewModel = viewModel
        self.publishers = publishers
    }
    
    public static func == (lhs: ObservableViewModel<ViewModel>, rhs: ObservableViewModel<ViewModel>) -> Bool {
        return ObjectIdentifier(lhs) == ObjectIdentifier(rhs)
    }
    
    public func hash(into hasher: inout Hasher) {
        hasher.combine(ObjectIdentifier(self))
    }
}
