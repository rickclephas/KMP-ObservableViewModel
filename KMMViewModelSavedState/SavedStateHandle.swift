//
//  SavedStateHandle.swift
//  KMMViewModelSavedState
//
//  Created by Rick Clephas on 11/01/2024.
//

import Foundation

public protocol SavedStateHandle: AnyObject {
    var data: Data? { get set }
    func setStateChangedListener(listener: @escaping (Data?) -> Void)
}
