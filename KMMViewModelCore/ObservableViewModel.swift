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

private var observableViewModelKey: Void?

private class WeakObservableViewModel<ViewModel: KMMViewModel> {
    weak var observableViewModel: ObservableViewModel<ViewModel>?
    init(_ observableViewModel: ObservableViewModel<ViewModel>) {
        self.observableViewModel = observableViewModel
    }
}

/// Gets the `ObservableObject` for the specified `KMMViewModel`.
/// - Parameter viewModel: The `KMMViewModel` to wrap in an `ObservableObject`.
public func observableViewModel<ViewModel: KMMViewModel>(
    for viewModel: ViewModel
) -> ObservableViewModel<ViewModel> {
    if let object = objc_getAssociatedObject(viewModel, &observableViewModelKey) {
        guard let observableViewModel = (object as! WeakObservableViewModel<ViewModel>).observableViewModel else {
            fatalError("ObservableViewModel has been deallocated")
        }
        return observableViewModel
    } else {
        let observableViewModel = ObservableViewModel(viewModel)
        let object = WeakObservableViewModel<ViewModel>(observableViewModel)
        objc_setAssociatedObject(viewModel, &observableViewModelKey, object, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        return observableViewModel
    }
}

/// Gets the `ObservableObject` for the specified `KMMViewModel`.
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
    
    internal var childViewModels: Dictionary<AnyKeyPath, AnyHashable> = [:]
    
    internal init(_ viewModel: ViewModel) {
        objectWillChange = ObservableViewModelPublisher(viewModel.viewModelScope, viewModel.objectWillChange)
        self.viewModel = viewModel
    }
    
    public static func == (lhs: ObservableViewModel<ViewModel>, rhs: ObservableViewModel<ViewModel>) -> Bool {
        return ObjectIdentifier(lhs) == ObjectIdentifier(rhs)
    }
    
    public func hash(into hasher: inout Hasher) {
        hasher.combine(ObjectIdentifier(self))
    }
}
