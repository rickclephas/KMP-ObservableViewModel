//
//  ViewModelScope.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 27/11/2022.
//

import Foundation

/// A view model scope of a `KMMViewModel`.
@objc(KMMVMViewModelScope) public protocol ViewModelScope: NSObjectProtocol {
    /// Increases the view model subscription count.
    @objc(increaseSubscriptionCount) func increaseSubscriptionCount()
    /// Decreases the view model subscription count.
    @objc(decreaseSubscriptionCount) func decreaseSubscriptionCount()
    /// Sets the `sendObjectWillChange` closure that will be called right before the view model changes.
    @objc(setSendObjectWillChange:) func setSendObjectWillChange(_ sendObjectWillChange: @escaping () -> Void)
    /// Cancels the view model scope and invokes the `onCleared` function of the view model.
    @objc(cancel) func cancel()
}
