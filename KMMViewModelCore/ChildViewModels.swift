//
//  ChildViewModels.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 02/07/2023.
//

import KMMViewModelCoreObjC

public extension KMMViewModel {
    
    private func setChildViewModels(_ keyPath: AnyKeyPath, _ viewModels: AnyHashable?) {
        if let viewModels = viewModels {
            observableViewModel(for: self).childViewModels[keyPath] = viewModels
        } else {
            observableViewModel(for: self).childViewModels.removeValue(forKey: keyPath)
        }
    }
    
    private func setChildViewModel<ViewModel: KMMViewModel>(
        _ viewModel: ViewModel?,
        at keyPath: AnyKeyPath
    ) {
        setChildViewModels(keyPath, observableViewModel(for: viewModel))
    }
    
    /// Stores a reference to the `ObservableObject` for the specified child `KMMViewModel`.
    func childViewModel<ViewModel: KMMViewModel>(
        _ viewModel: ViewModel?,
        at keyPath: KeyPath<Self, ViewModel?>
    ) -> ViewModel? {
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    /// Stores a reference to the `ObservableObject` for the specified child `KMMViewModel`.
    func childViewModel<ViewModel: KMMViewModel>(
        _ viewModel: ViewModel,
        at keyPath: KeyPath<Self, ViewModel>
    ) -> ViewModel {
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    // MARK: Arrays
    
    private func setChildViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel?]?,
        at keyPath: AnyKeyPath
    ) {
        setChildViewModels(keyPath, viewModels?.map { viewModel in
            observableViewModel(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel?]?,
        at keyPath: KeyPath<Self, [ViewModel?]?>
    ) -> [ViewModel?]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel?],
        at keyPath: KeyPath<Self, [ViewModel?]>
    ) -> [ViewModel?] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel]?,
        at keyPath: KeyPath<Self, [ViewModel]?>
    ) -> [ViewModel]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel],
        at keyPath: KeyPath<Self, [ViewModel]>
    ) -> [ViewModel] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    // MARK: Sets
    
    private func setChildViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel?>?,
        at keyPath: AnyKeyPath
    ) {
        setChildViewModels(keyPath, viewModels?.map { viewModel in
            observableViewModel(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel?>?,
        at keyPath: KeyPath<Self, Set<ViewModel?>?>
    ) -> Set<ViewModel?>? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel?>,
        at keyPath: KeyPath<Self, Set<ViewModel?>>
    ) -> Set<ViewModel?> {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel>?,
        at keyPath: KeyPath<Self, Set<ViewModel>?>
    ) -> Set<ViewModel>? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel>,
        at keyPath: KeyPath<Self, Set<ViewModel>>
    ) -> Set<ViewModel> {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    // MARK: Dictionaries
    
    private func setChildViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel?]?,
        at keyPath: AnyKeyPath
    ) {
        setChildViewModels(keyPath, viewModels?.mapValues { viewModel in
            observableViewModel(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel?]?,
        at keyPath: KeyPath<Self, [Key : ViewModel?]?>
    ) -> [Key : ViewModel?]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel?],
        at keyPath: KeyPath<Self, [Key : ViewModel?]>
    ) -> [Key : ViewModel?] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel]?,
        at keyPath: KeyPath<Self, [Key : ViewModel]?>
    ) -> [Key : ViewModel]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel],
        at keyPath: KeyPath<Self, [Key : ViewModel]>
    ) -> [Key : ViewModel] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
}
