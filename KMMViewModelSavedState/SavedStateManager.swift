//
//  SavedStateManager.swift
//  KMMViewModelSavedState
//
//  Created by Rick Clephas on 12/01/2024.
//

import Combine
import SwiftUI

public extension View {
    
    @available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
    func savedStateManager() -> some View {
        modifier(SavedStateManagerViewModifier())
    }
}

@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
private struct SavedStateManagerViewModifier: ViewModifier {
    
    @SceneStorage("KMMViewModelSavedState") private var state: Data?
    @StateObject private var manager = SavedStateManager()
    
    func body(content: Content) -> some View {
        content.environmentObject(manager).onReceive(manager.stateChanged) {
            state = manager.getState()
        }.onAppear {
            manager.setState(state)
        }
    }
}

@available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *)
internal class SavedStateManager: ObservableObject {
    
    let stateChanged = ObservableObjectPublisher()
    
    private var states: [String:Data] = [:]
    private var state: Data? = nil
    
    func getState(for key: String) -> Data? { states[key] }
    
    func setState(_ state: Data?, for key: String) {
        if let state {
            states[key] = state
        } else {
            states.removeValue(forKey: key)
        }
        self.state = try! NSKeyedArchiver.archivedData(withRootObject: states, requiringSecureCoding: true)
        stateChanged.send()
    }
    
    func getState() -> Data? { state }
    
    func setState(_ state: Data?) {
        guard let state, self.state == nil else { return }
        self.state = state
        states = try! NSKeyedUnarchiver.unarchivedDictionary(
            ofKeyClass: NSString.self, objectClass: NSData.self, from: state
        ) as [String : Data]? ?? [:]
    }
}
