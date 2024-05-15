//
//  ViewModel.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 28/11/2022.
//

import Combine
import KMPObservableViewModelCoreObjC

/// A Kotlin Multiplatform Mobile ViewModel.
public protocol ViewModel: ObservableObject where ObjectWillChangePublisher == ObservableObjectPublisher {
    /// The `ViewModelScope` of this `ViewModel`.
    var viewModelScope: ViewModelScope { get }
    /// Internal KMP-ObservableViewModel function used to clear the ViewModel.
    /// - Warning: You should NOT call this yourself!
    func clear()
}
