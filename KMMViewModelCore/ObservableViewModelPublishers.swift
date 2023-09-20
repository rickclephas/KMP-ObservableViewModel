//
//  ObservableViewModelPublishers.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 20/09/2023.
//

import Foundation

private var observableViewModelPublishersKey: Void?

private class WeakObservableViewModelPublishers {
    weak var publishers: ObservableViewModelPublishers?
    init(_ publishers: ObservableViewModelPublishers) {
        self.publishers = publishers
    }
}

/// Gets the `ObservableViewModelPublishers` for the specified `viewModel`.
internal func observableViewModelPublishers<ViewModel: KMMViewModel>(
    for viewModel: ViewModel
) -> ObservableViewModelPublishers {
    let publishers: ObservableViewModelPublishers
    if let object = objc_getAssociatedObject(viewModel, &observableViewModelPublishersKey) {
        publishers = (object as! WeakObservableViewModelPublishers).publishers ?? {
            fatalError("ObservableViewModel has been deallocated")
        }()
    } else {
        let publisher = ObservableViewModelPublisher(viewModel.viewModelScope, viewModel.objectWillChange)
        publishers = ObservableViewModelPublishers(publisher)
        let object = WeakObservableViewModelPublishers(publishers)
        objc_setAssociatedObject(viewModel, &observableViewModelPublishersKey, object, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
    }
    return publishers
}

/// Gets the `ObservableViewModelPublishers` for the specified `viewModel`.
internal func observableViewModelPublishers<ViewModel: KMMViewModel>(
    for viewModel: ViewModel?
) -> ObservableViewModelPublishers? {
    guard let viewModel = viewModel else { return nil }
    let observableViewModelPublishers = observableViewModelPublishers(for: viewModel)
    return observableViewModelPublishers
}

/// Helper object that keeps strong references to the `ObservableViewModelPublisher`s.
internal final class ObservableViewModelPublishers: Hashable {
    let publisher: ObservableViewModelPublisher
    var childPublishers: Dictionary<AnyKeyPath, AnyHashable> = [:]
    
    init(_ publisher: ObservableViewModelPublisher) {
        self.publisher = publisher
    }
    
    static func == (lhs: ObservableViewModelPublishers, rhs: ObservableViewModelPublishers) -> Bool {
        return ObjectIdentifier(lhs) == ObjectIdentifier(rhs)
    }
    
    func hash(into hasher: inout Hasher) {
        hasher.combine(ObjectIdentifier(self))
    }
}
