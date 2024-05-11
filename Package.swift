// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "KMPObservableViewModel",
    platforms: [.iOS(.v13), .macOS(.v10_15), .tvOS(.v13), .watchOS(.v6)],
    products: [
        .library(
            name: "KMPObservableViewModelCore",
            targets: ["KMPObservableViewModelCore"]
        ),
        .library(
            name: "KMPObservableViewModelSwiftUI",
            targets: ["KMPObservableViewModelSwiftUI"]
        )
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
            name: "KMPObservableViewModelSwiftUI",
            dependencies: [.target(name: "KMPObservableViewModelCore")],
            path: "KMPObservableViewModelSwiftUI"
        )
    ],
    swiftLanguageVersions: [.v5]
)
