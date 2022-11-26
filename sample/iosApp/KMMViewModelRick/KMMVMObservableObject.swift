//
//  KMMVMObservableObject.swift
//  KMMViewModelSample
//
//  Created by Rick Clephas on 24/11/2022.
//

import Foundation
import Combine
import SwiftUI

@objc(KMMVMObservableObjectScope) public protocol KMMVMObservableObjectScope: NSObjectProtocol {
    @objc(setSubscriptionCount:) func setSubscriptionCount(_ subscriptionCount: Int)
    @objc(setSendObjectWillChange:) func setSendObjectWillChange(_ sendObjectWillChange: @escaping () -> Void)
    @objc(callOnCleared) func callOnCleared()
}

public extension ObservedObject {
    init<ViewModel>(
        wrappedValue: ViewModel,
        _ keyPath: KeyPath<ViewModel, KMMVMObservableObjectScope>
    ) where ObjectType == KMMObservableObject<ViewModel> {
        self.init(wrappedValue: KMMObservableObject(keyPath: keyPath, viewModel: wrappedValue))
    }
}

public extension StateObject {
    init<ViewModel>(
        wrappedValue: @autoclosure @escaping () -> ViewModel,
        _ keyPath: KeyPath<ViewModel, KMMVMObservableObjectScope>
    ) where ObjectType == KMMObservableObject<ViewModel> {
        self.init(wrappedValue: KMMObservableObject(keyPath: keyPath, viewModel: wrappedValue()))
    }
}

@dynamicMemberLookup
public class KMMObservableObject<ViewModel>: ObservableObject {
    
    public let objectWillChange: ObservableObjectPublisher
    public var viewModel: ViewModel
    
    public init(keyPath: KeyPath<ViewModel, KMMVMObservableObjectScope>, viewModel: ViewModel) {
        objectWillChange = ObservableObjectPublisher(viewModel[keyPath: keyPath])
        self.viewModel = viewModel
    }
    
    public subscript<T>(dynamicMember keyPath: KeyPath<ViewModel, T>) -> T {
        get { viewModel[keyPath: keyPath] }
    }
    
    public subscript<T>(dynamicMember keyPath: WritableKeyPath<ViewModel, T>) -> T {
        get { viewModel[keyPath: keyPath] }
        set { viewModel[keyPath: keyPath] = newValue }
    }
}

public class ObservableObjectPublisher: Publisher {
    public typealias Output = Void
    public typealias Failure = Never
    
    private var subscriptionCount = 0 // TODO: Make atomic
    private let wrapped = Combine.ObservableObjectPublisher()
    private weak var observableObjectScope: KMMVMObservableObjectScope?
    
    init(_ observableObjectScope: KMMVMObservableObjectScope) {
        Swift.print("ObservableObjectPublisher init")
        self.observableObjectScope = observableObjectScope
        observableObjectScope.setSendObjectWillChange { [weak self] in
            self?.wrapped.send()
        }
    }
    
    public func receive<S>(subscriber: S) where S : Subscriber, Never == S.Failure, Void == S.Input {
        Swift.print("ObservableObjectPublisher receive subscriber")
        increaseSubscriptionCount()
        wrapped.receive(subscriber: ObservableObjectSubscriber(self, subscriber))
    }
    
    func increaseSubscriptionCount() {
        subscriptionCount += 1
        observableObjectScope?.setSubscriptionCount(subscriptionCount)
    }

    func decreaseSubscriptionCount() {
        subscriptionCount -= 1
        observableObjectScope?.setSubscriptionCount(subscriptionCount)
    }
    
    deinit {
        Swift.print("ObservableObjectPublisher deinit")
        observableObjectScope?.callOnCleared()
    }
}

class ObservableObjectSubscriber<S>: Subscriber where S : Subscriber, Never == S.Failure, Void == S.Input {
    typealias Input = Void
    typealias Failure = Never
    
    private let publisher: ObservableObjectPublisher
    private let subscriber: S
    
    init(_ publisher: ObservableObjectPublisher, _ subscriber: S) {
        self.publisher = publisher
        self.subscriber = subscriber
    }
    
    func receive(subscription: Subscription) {
        subscriber.receive(subscription: ObservableObjectSubscription(publisher, subscription))
    }
    
    func receive(_ input: Void) -> Subscribers.Demand {
        subscriber.receive(input)
    }
    
    func receive(completion: Subscribers.Completion<Never>) {
        Swift.print("ObservableObjectSubscriber receive completion")
        subscriber.receive(completion: completion)
    }
}

class ObservableObjectSubscription: Subscription {
    
    private let publisher: ObservableObjectPublisher
    private let subscription: Subscription
    
    init(_ publisher: ObservableObjectPublisher, _ subscription: Subscription) {
        self.publisher = publisher
        self.subscription = subscription
    }
    
    func request(_ demand: Subscribers.Demand) {
        subscription.request(demand)
    }
    
    private var cancelled = false
    
    func cancel() {
        Swift.print("ObservableObjectSubscription cancel")
        subscription.cancel()
        guard !cancelled else { return }
        cancelled = true
        publisher.decreaseSubscriptionCount()
    }
}
