// swift-tools-version:5.9

import PackageDescription
import CompilerPluginSupport

let package = Package(
    name: "KMPObservableViewModel",
    platforms: [.iOS(.v13), .macOS(.v10_15), .tvOS(.v13), .watchOS(.v6)],
    products: [
        .library(
            name: "KMPObservableViewModelCore",
            targets: ["KMPObservableViewModelCore"]
        ),
        .library(
            name: "KMPObservableViewModelProperties",
            targets: ["KMPObservableViewModelProperties"]
        ),
        .library(
            name: "KMPObservableViewModelSwiftUI",
            targets: ["KMPObservableViewModelSwiftUI"]
        )
    ],
    dependencies: [
        .package(url: "https://github.com/swiftlang/swift-syntax", from: "509.0.0")
    ],
    targets: [
        .target(
            name: "KMPObservableViewModelCoreObjC",
            path: "KMPObservableViewModelCoreObjC",
            publicHeadersPath: "."
        ),
        .target(
            name: "KMPObservableViewModelCore",
            dependencies: [.target(name: "KMPObservableViewModelCoreObjC")],
            path: "KMPObservableViewModelCore"
        ),
        .target(
            name: "KMPObservableViewModelProperties",
            dependencies: [
                .target(name: "KMPObservableViewModelCore"),
                .target(name: "KMPObservableViewModelPlugin"),
            ],
            path: "KMPObservableViewModelProperties"
        ),
        .target(
            name: "KMPObservableViewModelSwiftUI",
            dependencies: [.target(name: "KMPObservableViewModelCore")],
            path: "KMPObservableViewModelSwiftUI"
        ),
        .macro(
            name: "KMPObservableViewModelPlugin",
            dependencies: [
                .product(name: "SwiftSyntaxMacros", package: "swift-syntax"),
                .product(name: "SwiftCompilerPlugin", package: "swift-syntax")
            ],
            path: "KMPObservableViewModelPlugin"
        ),
    ],
    swiftLanguageVersions: [.v5]
)
