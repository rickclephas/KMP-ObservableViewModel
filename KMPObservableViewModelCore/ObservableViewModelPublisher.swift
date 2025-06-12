//
//  ObservableViewModelPublisher.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 20/09/2023.
//

import Combine
import Observation
import KMPObservableViewModelCoreObjC

/// Publisher for `ObservableViewModel` that connects to the `ViewModelScope`.
public final class ObservableViewModelPublisher: Combine.Publisher, KMPObservableViewModelCoreObjC.Publisher {
    public typealias Output = Void
    public typealias Failure = Never
    
    private var _observationRegistrar: Any? = nil
    @available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
    public var observationRegistrar: ObservationRegistrar {
        if let observationRegistrar = _observationRegistrar {
            return observationRegistrar as! ObservationRegistrar
        }
        let observationRegistrar = ObservationRegistrar()
        _observationRegistrar = observationRegistrar
        return observationRegistrar
    }
    
    internal let cancellable = ViewModelCancellable()
    
    private let publisher: ObservableObjectPublisher
    private let subscriptionCount: any SubscriptionCount
    
    internal init(_ viewModel: any ViewModel) {
        self.publisher = viewModel.objectWillChange
        self.subscriptionCount = viewModel.viewModelScope.subscriptionCount
    }
    
    public func receive<S>(subscriber: S) where S : Subscriber, Never == S.Failure, Void == S.Input {
        subscriptionCount.increase()
        publisher.receive(subscriber: ObservableViewModelSubscriber(subscriptionCount, subscriber))
    }
    
    public func send() {
        publisher.send()
    }
}

internal extension KMPObservableViewModelCoreObjC.Publisher {
    /// Casts this `Publisher` to an `ObservableViewModelPublisher`.
    func cast() -> ObservableViewModelPublisher {
        guard let publisher = self as? ObservableViewModelPublisher else {
            fatalError("Publisher must be an ObservableViewModelPublisher")
        }
        return publisher
    }
}

/// Subscriber for `ObservableViewModelPublisher` that creates `ObservableViewModelSubscription`s.
private class ObservableViewModelSubscriber<S>: Subscriber where S : Subscriber, Never == S.Failure, Void == S.Input {
    typealias Input = Void
    typealias Failure = Never
    
    private let subscriptionCount: any SubscriptionCount
    private let subscriber: S
    
    init(_ subscriptionCount: any SubscriptionCount, _ subscriber: S) {
        self.subscriptionCount = subscriptionCount
        self.subscriber = subscriber
    }
    
    func receive(subscription: Subscription) {
        subscriber.receive(subscription: ObservableViewModelSubscription(subscriptionCount, subscription))
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
    
    private var subscriptionCount: (any SubscriptionCount)?
    private let subscription: Subscription
    
    init(_ subscriptionCount: any SubscriptionCount, _ subscription: Subscription) {
        self.subscriptionCount = subscriptionCount
        self.subscription = subscription
    }
    
    func request(_ demand: Subscribers.Demand) {
        subscription.request(demand)
    }
    
    func cancel() {
        subscription.cancel()
        subscriptionCount?.decrease()
        subscriptionCount = nil
    }
}
