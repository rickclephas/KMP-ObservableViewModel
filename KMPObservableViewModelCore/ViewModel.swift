//
//  ViewModel.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 28/11/2022.
//

import Combine
import KMPObservableViewModelCoreObjC

/// A Kotlin Multiplatform ViewModel.
public protocol ViewModel: ObservableObject where ObjectWillChangePublisher == ObservableObjectPublisher {
    /// The `ViewModelScope` of this `ViewModel`.
    var viewModelScope: ViewModelScope { get }
    /// An `ObservableViewModelPublisher` that emits before this `ViewModel` has changed.
    var viewModelWillChange: ObservableViewModelPublisher { get }
    /// Internal KMP-ObservableViewModel function used to clear the ViewModel.
    /// - Warning: You should NOT call this yourself!
    func clear()
}

public extension ViewModel {
    var viewModelWillChange: ObservableViewModelPublisher {
        if let publisher = viewModelScope.publisher {
            return publisher as! ObservableViewModelPublisher
        }
        let publisher = ObservableViewModelPublisher(self)
        viewModelScope.publisher = publisher
        return publisher
    }
}
