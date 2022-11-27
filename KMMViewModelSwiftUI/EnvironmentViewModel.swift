//
//  EnvironmentViewModel.swift
//  KMMViewModelSwiftUI
//
//  Created by Rick Clephas on 27/11/2022.
//

import SwiftUI
import KMMViewModelCore

/// An `EnvironmentObject` property wrapper for `KMMViewModel`s.
@propertyWrapper
public struct EnvironmentViewModel<ViewModel>: DynamicProperty {
    
    @EnvironmentObject private var observableObject: ObservableViewModel<ViewModel>
    
    /// The underlying `KMMViewModel` referenced by the `EnvironmentViewModel`.
    public var wrappedValue: ViewModel { observableObject.viewModel }
    
    /// A projection of the observed `KMMViewModel` that creates bindings to its properties using dynamic member lookup.
    public lazy var projectedValue: ObservableViewModel<ViewModel>.Projection = {
        ObservableViewModel.Projection(observableObject)
    }()
    
    /// Creates an `EnvironmentViewModel`.
    public init() { }
}

public extension View {
    /// Supplies a `KMMViewModel` to a view subhierarchy.
    /// - Parameters:
    ///     - viewModel: The `KMMViewModel` to supply to a view subhierarchy.
    ///     - keyPath: The key path to the `ViewModelScope` property of the ViewModel.
    func environmentViewModel<ViewModel>(
        for viewModel: ViewModel,
        with keyPath: KeyPath<ViewModel, ViewModelScope>
    ) -> some View {
        environmentObject(createObservableViewModel(for: viewModel, with: keyPath))
    }
}
