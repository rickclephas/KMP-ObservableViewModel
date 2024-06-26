//
//  ChildViewModels.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 02/07/2023.
//

import KMPObservableViewModelCoreObjC

public extension ViewModel {
    
    private func setChildViewModelPublishers(_ keyPath: AnyKeyPath, _ publishers: AnyHashable?) {
        if let publishers = publishers {
            observableViewModelPublishers(for: self).childPublishers[keyPath] = publishers
        } else {
            observableViewModelPublishers(for: self).childPublishers.removeValue(forKey: keyPath)
        }
    }
    
    private func setChildViewModel<VM: ViewModel>(
        _ viewModel: VM?,
        at keyPath: AnyKeyPath
    ) {
        setChildViewModelPublishers(keyPath, observableViewModelPublishers(for: viewModel))
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
        setChildViewModelPublishers(keyPath, viewModels?.map { viewModel in
            observableViewModelPublishers(for: viewModel)
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
        setChildViewModelPublishers(keyPath, viewModels?.map { viewModel in
            observableViewModelPublishers(for: viewModel)
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
        setChildViewModelPublishers(keyPath, viewModels?.mapValues { viewModel in
            observableViewModelPublishers(for: viewModel)
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
