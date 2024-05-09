//
//  KMMViewModel.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 28/11/2022.
//

import Combine
import KMMViewModelCoreObjC

/// A Kotlin Multiplatform Mobile ViewModel.
public protocol KMMViewModel: ObservableObject where ObjectWillChangePublisher == ObservableObjectPublisher {
    /// The `ViewModelScope` of this `KMMViewModel`.
    var viewModelScope: ViewModelScope { get }
    /// Internal KMM-ViewModel function used to clear the ViewModel.
    /// - Warning: You should NOT call this yourself!
    func clear()
}
