//
//  ChildViewModels.swift
//  KMMViewModelCore
//
//  Created by Rick Clephas on 02/07/2023.
//

import KMMViewModelCoreObjC

public extension KMMViewModel {
    
    private func setChildViewModelPublishers(_ keyPath: AnyKeyPath, _ publishers: AnyHashable?) {
        if let publishers = publishers {
            observableViewModelPublishers(for: self).childPublishers[keyPath] = publishers
        } else {
            observableViewModelPublishers(for: self).childPublishers.removeValue(forKey: keyPath)
        }
    }
    
    private func setChildViewModel<ViewModel: KMMViewModel>(
        _ viewModel: ViewModel?,
        at keyPath: AnyKeyPath
    ) {
        setChildViewModelPublishers(keyPath, observableViewModelPublishers(for: viewModel))
    }
    
    /// Stores a reference to the `ObservableObject` for the specified child `KMMViewModel`.
    func childViewModel<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, ViewModel?>
    ) -> ViewModel? {
        let viewModel = self[keyPath: keyPath]
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModel parameter")
    func childViewModel<ViewModel: KMMViewModel>(
        _ viewModel: ViewModel?,
        at keyPath: KeyPath<Self, ViewModel?>
    ) -> ViewModel? {
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    /// Stores a reference to the `ObservableObject` for the specified child `KMMViewModel`.
    func childViewModel<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, ViewModel>
    ) -> ViewModel {
        let viewModel = self[keyPath: keyPath]
        setChildViewModel(viewModel, at: keyPath)
        return viewModel
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModel parameter")
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
        setChildViewModelPublishers(keyPath, viewModels?.map { viewModel in
            observableViewModelPublishers(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [ViewModel?]?>
    ) -> [ViewModel?]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel?]?,
        at keyPath: KeyPath<Self, [ViewModel?]?>
    ) -> [ViewModel?]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [ViewModel?]>
    ) -> [ViewModel?] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel?],
        at keyPath: KeyPath<Self, [ViewModel?]>
    ) -> [ViewModel?] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [ViewModel]?>
    ) -> [ViewModel]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: [ViewModel]?,
        at keyPath: KeyPath<Self, [ViewModel]?>
    ) -> [ViewModel]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [ViewModel]>
    ) -> [ViewModel] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
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
        setChildViewModelPublishers(keyPath, viewModels?.map { viewModel in
            observableViewModelPublishers(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, Set<ViewModel?>?>
    ) -> Set<ViewModel?>? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel?>?,
        at keyPath: KeyPath<Self, Set<ViewModel?>?>
    ) -> Set<ViewModel?>? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, Set<ViewModel?>>
    ) -> Set<ViewModel?> {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel?>,
        at keyPath: KeyPath<Self, Set<ViewModel?>>
    ) -> Set<ViewModel?> {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, Set<ViewModel>?>
    ) -> Set<ViewModel>? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<ViewModel: KMMViewModel>(
        _ viewModels: Set<ViewModel>?,
        at keyPath: KeyPath<Self, Set<ViewModel>?>
    ) -> Set<ViewModel>? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, Set<ViewModel>>
    ) -> Set<ViewModel> {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
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
        setChildViewModelPublishers(keyPath, viewModels?.mapValues { viewModel in
            observableViewModelPublishers(for: viewModel)
        })
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [Key : ViewModel?]?>
    ) -> [Key : ViewModel?]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel?]?,
        at keyPath: KeyPath<Self, [Key : ViewModel?]?>
    ) -> [Key : ViewModel?]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [Key : ViewModel?]>
    ) -> [Key : ViewModel?] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel?],
        at keyPath: KeyPath<Self, [Key : ViewModel?]>
    ) -> [Key : ViewModel?] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [Key : ViewModel]?>
    ) -> [Key : ViewModel]? {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel]?,
        at keyPath: KeyPath<Self, [Key : ViewModel]?>
    ) -> [Key : ViewModel]? {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    /// Stores references to the `ObservableObject`s of the specified child `KMMViewModel`s.
    func childViewModels<Key, ViewModel: KMMViewModel>(
        at keyPath: KeyPath<Self, [Key : ViewModel]>
    ) -> [Key : ViewModel] {
        let viewModels = self[keyPath: keyPath]
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
    
    @available(*, deprecated, message: "Please use the variant without the viewModels parameter")
    func childViewModels<Key, ViewModel: KMMViewModel>(
        _ viewModels: [Key : ViewModel],
        at keyPath: KeyPath<Self, [Key : ViewModel]>
    ) -> [Key : ViewModel] {
        setChildViewModels(viewModels, at: keyPath)
        return viewModels
    }
}
