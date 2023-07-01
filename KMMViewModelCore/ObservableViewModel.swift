//
//  ObservableViewModel.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 27/11/2022.
//

import Combine
import KMMViewModelCoreObjC
import os.log

@available(*, deprecated, renamed: "observableViewModel")
public func createObservableViewModel<ViewModel: KMMViewModel>(
    for viewModel: ViewModel
) -> ObservableViewModel<ViewModel> {
    observableViewModel(for: viewModel)
}

private var observableViewModelKey = "observableViewModel"

/// Gets an `ObservableObject` for the specified `KMMViewModel`.
/// - Parameter viewModel: The `KMMViewModel` to wrap in an `ObservableObject`.
public func observableViewModel<ViewModel: KMMViewModel>(
    for viewModel: ViewModel
) -> ObservableViewModel<ViewModel> {
    if let observableViewModel = objc_getAssociatedObject(viewModel, &observableViewModelKey) {
        return observableViewModel as! ObservableViewModel<ViewModel>
    }
    let observableViewModel = ObservableViewModel(viewModel)
    objc_setAssociatedObject(viewModel, &observableViewModelKey, observableViewModel, .OBJC_ASSOCIATION_ASSIGN)
    return observableViewModel
}

public func resetObservableViewModel<ViewModel: KMMViewModel>(viewModel: ViewModel) {
    os_log("resetObservableViewModel %@", String(describing: viewModel))
    objc_setAssociatedObject(viewModel, &observableViewModelKey, nil, .OBJC_ASSOCIATION_ASSIGN)
}

/// An `ObservableObject` for a `KMMViewModel`.
public final class ObservableViewModel<ViewModel: KMMViewModel>: ObservableObject {
    
    public let objectWillChange: ObservableViewModelPublisher
    
    public let viewModel: ViewModel
    
    internal init(_ viewModel: ViewModel) {
        objectWillChange = ObservableViewModelPublisher(viewModel.viewModelScope, viewModel.objectWillChange)
        self.viewModel = viewModel
        os_log("init ObservableViewModel %@", String(describing: viewModel))
    }

    deinit {
        os_log("deinit ObservableViewModel %@", String(describing: self.viewModel))
    }
}

/// Publisher for `ObservableViewModel` that connects to the `ViewModelScope`.
public final class ObservableViewModelPublisher: Publisher {
    public typealias Output = Void
    public typealias Failure = Never
    
    internal weak var viewModelScope: ViewModelScope?
    
    private let publisher = ObservableObjectPublisher()
    private var objectWillChangeCancellable: AnyCancellable? = nil
    
    init(_ viewModelScope: ViewModelScope, _ objectWillChange: ObservableObjectPublisher) {
        self.viewModelScope = viewModelScope
        viewModelScope.setSendObjectWillChange { [weak self] in
            self?.publisher.send()
        }
        objectWillChangeCancellable = objectWillChange.sink { [weak self] _ in
            self?.publisher.send()
        }
        os_log("init ObservableViewModelPublisher %@", String(describing: self))
    }
    
    public func receive<S>(subscriber: S) where S : Subscriber, Never == S.Failure, Void == S.Input {
        viewModelScope?.increaseSubscriptionCount()
        publisher.receive(subscriber: ObservableViewModelSubscriber(self, subscriber))
    }
    
    deinit {
        os_log("deinit ObservableViewModelPublisher %@", String(describing: self))
        viewModelScope?.cancel()
    }
}

/// Subscriber for `ObservableViewModelPublisher` that creates `ObservableViewModelSubscription`s.
private class ObservableViewModelSubscriber<S>: Subscriber where S : Subscriber, Never == S.Failure, Void == S.Input {
    typealias Input = Void
    typealias Failure = Never
    
    private let publisher: ObservableViewModelPublisher
    private let subscriber: S
    
    init(_ publisher: ObservableViewModelPublisher, _ subscriber: S) {
        self.publisher = publisher
        self.subscriber = subscriber
        os_log("init ObservableViewModelSubscriber %@", String(describing: self))
    }
    
    deinit {
        os_log("deinit ObservableViewModelSubscriber %@", String(describing: self))
    }

    func receive(subscription: Subscription) {
        subscriber.receive(subscription: ObservableViewModelSubscription(publisher, subscription))
    }
    
    func receive(_ input: Void) -> Subscribers.Demand {
        subscriber.receive(input)
    }
    
    func receive(completion: Subscribers.Completion<Never>) {
        subscriber.receive(completion: completion)
    }
}

/// Subscription for `ObservableViewModelPublisher` that decreases the subscription count upon cancellation.
private class ObservableViewModelSubscription: Subscription {
    
    private let publisher: ObservableViewModelPublisher
    private let subscription: Subscription
    
    init(_ publisher: ObservableViewModelPublisher, _ subscription: Subscription) {
        self.publisher = publisher
        self.subscription = subscription
        os_log("init ObservableViewModelSubscription %@", String(describing: self))
    }
    
    deinit {
        os_log("deinit ObservableViewModelSubscription %@", String(describing: self))
    }

    func request(_ demand: Subscribers.Demand) {
        subscription.request(demand)
    }
    
    private var cancelled = false
    
    func cancel() {
        subscription.cancel()
        guard !cancelled else { return }
        cancelled = true
        publisher.viewModelScope?.decreaseSubscriptionCount()
    }
}
