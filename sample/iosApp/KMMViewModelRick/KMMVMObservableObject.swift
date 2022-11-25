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
    @objc(setObjectWillChangeSubscriptionCount:) func setObjectWillChangeSubscriptionCount(subscriptionCount: Int)
    @objc(setSendObjectWillChange:) func setSendObjectWillChange(sendObjectWillChange: @escaping () -> Void)
}

public extension ObservedObject {
    init<ViewModel>(wrappedValue: ViewModel, _ keyPath: KeyPath<ViewModel, KMMVMObservableObjectScope>) where ObjectType == KMMObservableObject<ViewModel> {
        self.init(wrappedValue: KMMObservableObject(keyPath: keyPath, viewModel: wrappedValue))
    }
}

@dynamicMemberLookup
public class KMMObservableObject<ViewModel>: ObservableObject {
    
    public var viewModel: ViewModel
    
    public init(keyPath: KeyPath<ViewModel, KMMVMObservableObjectScope>, viewModel: ViewModel) {
        let observableObjectScope = viewModel[keyPath: keyPath]
        self.viewModel = viewModel
        observableObjectScope.setSendObjectWillChange { [weak self] in
            self?.objectWillChange.send()
        }
    }
    
    public subscript<T>(dynamicMember keyPath: KeyPath<ViewModel, T>) -> T {
        get { viewModel[keyPath: keyPath] }
    }
    
    public subscript<T>(dynamicMember keyPath: WritableKeyPath<ViewModel, T>) -> T {
        get { viewModel[keyPath: keyPath] }
        set { viewModel[keyPath: keyPath] = newValue }
    }
}
