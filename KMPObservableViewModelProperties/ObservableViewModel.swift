//
//  ObservableViewModel.swift
//  KMPObservableViewModelProperties
//
//  Created by Rick Clephas on 21/06/2025.
//

@attached(member, conformances: ViewModel, names: named(subscript(dynamicMember:)))
public macro ObservableViewModel() = #externalMacro(module: "KMPObservableViewModelPlugin", type: "ObservableViewModel")
