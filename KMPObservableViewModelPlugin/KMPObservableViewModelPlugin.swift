//
//  KMPObservableViewModelPlugin.swift
//  KMPObservableViewModelPlugin
//
//  Created by Rick Clephas on 21/06/2025.
//

import SwiftCompilerPlugin
import SwiftSyntaxMacros

@main
struct KMPObservableViewModelPlugin: CompilerPlugin {
    var providingMacros: [any Macro.Type] = [
        ObservableViewModel.self
    ]
}
