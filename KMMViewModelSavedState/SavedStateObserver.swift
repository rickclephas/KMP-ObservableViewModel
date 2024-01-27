//
//  SavedStateObserver.swift
//  KMMViewModelSavedState
//
//  Created by Rick Clephas on 11/01/2024.
//

import Combine
import SwiftUI

public extension View {
    
    @available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
    func savedState(_ key: String, _ savedStateHandle: SavedStateHandle) -> some View {
        modifier(SavedStateObserverViewModifier(key, savedStateHandle))
    }
}

@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
internal struct SavedStateObserverViewModifier: ViewModifier {
    
    @EnvironmentObject private var manager: SavedStateManager
    @StateObject private var observer: SavedStateObserver
    
    init(_ key: String, _ savedStateHandle: SavedStateHandle) {
        self._observer = StateObject(wrappedValue: {
            SavedStateObserver(key, savedStateHandle)
        }())
    }
    
    func body(content: Content) -> some View {
        content.onAppear {
            observer.setManager(manager)
        }
    }
}

@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
private class SavedStateObserver: ObservableObject {
    
    private let key: String
    private let savedStateHandle: SavedStateHandle
    private var manager: SavedStateManager? = nil
    
    init(_ key: String, _ savedStateHandle: SavedStateHandle) {
        self.key = key
        self.savedStateHandle = savedStateHandle
        savedStateHandle.setStateChangedListener { [weak self] data in
            guard let self else { return }
            self.manager?.setState(data, for: key)
        }
    }
    
    func setManager(_ manager: SavedStateManager) {
        guard self.manager == nil else { return }
        self.manager = manager
        savedStateHandle.data = manager.getState(for: key)
    }
    
    deinit {
        manager?.setState(nil, for: key)
    }
}
