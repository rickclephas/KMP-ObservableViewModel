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

/// Publisher for `ObservableViewModel` that connects to the `ViewModelScope`.
public final class ObservableViewModelPublisher: Publisher {
    public typealias Output = Void
    public typealias Failure = Never
    
    internal weak var viewModelScope: ViewModelScope?
    
    private let publisher = ObservableObjectPublisher()
    private var objectWillChangeCancellable: AnyCancellable? = nil
    
    internal init(_ viewModelScope: ViewModelScope, _ objectWillChange: ObservableObjectPublisher) {
        self.viewModelScope = viewModelScope
        viewModelScope.setSendObjectWillChange { [weak self] in
            self?.publisher.send()
        }
        objectWillChangeCancellable = objectWillChange.sink { [weak self] _ in
            self?.publisher.send()
        }
    }
    
    public func receive<S>(subscriber: S) where S : Subscriber, Never == S.Failure, Void == S.Input {
        viewModelScope?.increaseSubscriptionCount()
        publisher.receive(subscriber: ObservableViewModelSubscriber(self, subscriber))
    }
    
    deinit {
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
