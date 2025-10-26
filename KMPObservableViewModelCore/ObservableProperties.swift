//
//  ObservableProperties.swift
//  KMPObservableViewModelCore
//
//  Created by Rick Clephas on 25/10/2025.
//

import Observation
import KMPObservableViewModelCoreObjC

/// Helper object that maps any external `Property` to an `ObservableProperty`.
@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
internal final class ObservableProperties {
    
    private var properties: [ObjectIdentifier:ObservableProperty] = [:]
    
    private func observableProperty(_ property: any Property) -> ObservableProperty {
        let identifier = ObjectIdentifier(property)
        if let observableProperty = properties[identifier] { return observableProperty }
        let observableProperty = ObservableProperty(property)
        properties[identifier] = observableProperty
        return observableProperty
    }
    
    func access(_ property: any Property) {
        observableProperty(property).access()
    }
    
    func willSet(_ property: any Property) {
        observableProperty(property).willSet()
    }
    
    func didSet(_ property: any Property) {
        observableProperty(property).didSet()
    }
}

/// Helper object that turns an external `Property` into a Swift observable property.
@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
private final class ObservableProperty: Observable {
    
    private let registrar = ObservationRegistrar()
    private let property: any Property
    
    init(_ property: any Property) {
        self.property = property
    }
    
    var value: Any? { property.value }
    
    func access() {
        registrar.access(self, keyPath: \.value)
    }
    
    func willSet() {
        registrar.willSet(self, keyPath: \.value)
    }
    
    func didSet() {
        registrar.didSet(self, keyPath: \.value)
    }
}
