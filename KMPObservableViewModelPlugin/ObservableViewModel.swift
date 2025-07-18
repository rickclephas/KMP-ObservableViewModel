//
//  ObservableViewModel.swift
//  KMPObservableViewModelPlugin
//
//  Created by Rick Clephas on 21/06/2025.
//

import SwiftSyntax
import SwiftSyntaxMacros
import SwiftSyntaxMacroExpansion

public struct ObservableViewModel: MemberMacro {
    public static func expansion(
        of node: AttributeSyntax,
        providingMembersOf declaration: some DeclGroupSyntax,
        conformingTo protocols: [TypeSyntax],
        in context: some MacroExpansionContext
    ) throws -> [DeclSyntax] {
        guard let declaration = declaration.as(ExtensionDeclSyntax.self) else {
            throw MacroExpansionErrorMessage("@ObservableViewModel can only be applied to an extension")
        }
        guard protocols.isEmpty else {
            throw MacroExpansionErrorMessage("@ObservableViewModel type must be of type ViewModel")
        }
        let type = declaration.extendedType
        return [
            """
            public subscript<Value>(dynamicMember keyPath: KeyPath<\(type.trimmed).Properties, Value>) -> Value {
                if #available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *) {
                    __properties[observable: keyPath]
                } else {
                    __properties[published: keyPath]
                }
            }
            """,
            """
            public subscript<Value>(dynamicMember keyPath: ReferenceWritableKeyPath<\(type.trimmed).Properties, Value>) -> Value {
                get {
                    if #available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *) {
                        __properties[observable: keyPath]
                    } else {
                        __properties[published: keyPath]
                    }
                }
                set {
                    if #available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *) {
                        __properties[observable: keyPath] = newValue
                    } else {
                        __properties[published: keyPath] = newValue
                    }
                }
            }
            """
        ]
    }
}
