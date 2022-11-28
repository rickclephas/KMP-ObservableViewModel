//
//  KMMViewModel.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 28/11/2022.
//

import KMMViewModelCoreObjC

/// A Kotlin Multiplatform Mobile ViewModel.
public protocol KMMViewModel {
    /// The `ViewModelScope` of this `KMMViewModel`.
    var viewModelScope: ViewModelScope { get }
}
