//
//  Properties.swift
//  KMPObservableViewModelProperties
//
//  Created by Rick Clephas on 16/06/2025.
//

import KMPObservableViewModelCoreObjC

/// A class that stores all observable properties of a ViewModel.
public protocol Properties: AnyObject {
    func shouldRegisterKeyPath() -> Bool
    func registerKeyPath(_ keyPath: any ViewModelKeyPath)
}
