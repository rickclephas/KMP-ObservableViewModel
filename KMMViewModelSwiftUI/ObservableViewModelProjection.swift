//
//  ObservableViewModelProjection.swift
//  KMMViewModelSwiftUI
//
//  Created by Rick Clephas on 27/11/2022.
//

import SwiftUI
import KMMViewModelCore

public extension ObservableViewModel {
    
    /// A projection of a `ViewModel` that creates bindings to its properties using dynamic member lookup.
    @dynamicMemberLookup
    struct Projection {
        
        internal let observableObject: ObservableViewModel<VM>
        
        internal init(_ observableObject: ObservableViewModel<VM>) {
            self.observableObject = observableObject
        }
        
        public subscript<T>(dynamicMember keyPath: WritableKeyPath<VM, T>) -> Binding<T> {
            Binding {
                observableObject.viewModel[keyPath: keyPath]
            } set: { value in
                var viewModel = observableObject.viewModel
                viewModel[keyPath: keyPath] = value
            }
        }
    }
}
