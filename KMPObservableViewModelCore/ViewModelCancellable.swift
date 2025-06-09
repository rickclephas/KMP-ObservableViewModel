//
//  ViewModelCancellable.swift
//  KMPObservableViewModel
//
//  Created by Rick Clephas on 09/06/2025.
//

import Combine

/// Helper object that provides a weakly cached `Cancellable` for a `ViewModel`.
internal class ViewModelCancellable {
    
    private var didInit = false
    private weak var cancellable: AnyCancellable?
    
    private func get(_ viewModel: any ViewModel) -> AnyCancellable {
        guard didInit else {
            let cancellable = AnyCancellable {
                if let cancellable = viewModel as? Cancellable {
                    cancellable.cancel()
                }
                viewModel.clear()
            }
            self.cancellable = cancellable
            didInit = true
            return cancellable
        }
        guard let cancellable = self.cancellable else {
            fatalError("ObservableViewModel for \(viewModel) has been deallocated")
        }
        return cancellable
    }
    
    static func get(for viewModel: any ViewModel) -> AnyCancellable {
        viewModel.viewModelWillChange.cancellable.get(viewModel)
    }
}
