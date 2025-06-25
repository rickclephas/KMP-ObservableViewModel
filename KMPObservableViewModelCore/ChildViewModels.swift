//
//  ChildViewModels.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 02/07/2023.
//

import KMPObservableViewModelCoreObjC

public extension ViewModel {
    
    private func setChildViewModel<VM: ViewModel>(
        _ viewModel: VM?,
        at keyPath: AnyKeyPath
    ) {
        viewModelWillChange.cancellable.setChildCancellables(keyPath, ViewModelCancellable.get(for: viewModel))
    }
    
    /// Stores a reference to the `ObservableObject` for the specified child `ViewModel`.
    func childViewModel<VM: ViewModel>(
        at keyPath: KeyPath<Self, VM?>
    ) -> VM? {
        let viewModel = self[keyPath: keyPath]
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    /// Stores a reference to the `ObservableObject` for the specified child `ViewModel`.
    func childViewModel<VM: ViewModel>(
        at keyPath: KeyPath<Self, VM>
    ) -> VM {
        let viewModel = self[keyPath: keyPath]
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    // MARK: Arrays
    
    private func setChildViewModels<VM: ViewModel>(
        _ viewModels: [VM?]?,
        at keyPath: AnyKeyPath
    ) {
        viewModelWillChange.cancellable.setChildCancellables(keyPath, viewModels?.map { viewModel in
            ViewModelCancellable.get(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, [VM?]?>
    ) -> [VM?]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, [VM?]>
    ) -> [VM?] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, [VM]?>
    ) -> [VM]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, [VM]>
    ) -> [VM] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    // MARK: Sets
    
    private func setChildViewModels<VM: ViewModel>(
        _ viewModels: Set<VM?>?,
        at keyPath: AnyKeyPath
    ) {
        viewModelWillChange.cancellable.setChildCancellables(keyPath, viewModels?.map { viewModel in
            ViewModelCancellable.get(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, Set<VM?>?>
    ) -> Set<VM?>? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, Set<VM?>>
    ) -> Set<VM?> {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, Set<VM>?>
    ) -> Set<VM>? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<VM: ViewModel>(
        at keyPath: KeyPath<Self, Set<VM>>
    ) -> Set<VM> {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    // MARK: Dictionaries
    
    private func setChildViewModels<Key, VM: ViewModel>(
        _ viewModels: [Key : VM?]?,
        at keyPath: AnyKeyPath
    ) {
        viewModelWillChange.cancellable.setChildCancellables(keyPath, viewModels?.mapValues { viewModel in
            ViewModelCancellable.get(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<Key, VM: ViewModel>(
        at keyPath: KeyPath<Self, [Key : VM?]?>
    ) -> [Key : VM?]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<Key, VM: ViewModel>(
        at keyPath: KeyPath<Self, [Key : VM?]>
    ) -> [Key : VM?] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<Key, VM: ViewModel>(
        at keyPath: KeyPath<Self, [Key : VM]?>
    ) -> [Key : VM]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `ViewModel`s.
    func childViewModels<Key, VM: ViewModel>(
        at keyPath: KeyPath<Self, [Key : VM]>
    ) -> [Key : VM] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
}
