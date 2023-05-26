// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "KMMViewModel",
    platforms: [.iOS(.v13), .macOS(.v10_15), .tvOS(.v13), .watchOS(.v6)],
    products: [
        .library(
            name: "KMMViewModelCore",
            targets: ["KMMViewModelCore"]
        ),
        .library(
            name: "KMMViewModelSwiftUI",
            targets: ["KMMViewModelSwiftUI"]
        ),
        .library(
            name: "KMMViewModelState",
            targets: ["KMMViewModelState"]
        )
    ],
    targets: [
        .target(
            name: "KMMViewModelCoreObjC",
            path: "KMMViewModelCoreObjC",
            publicHeadersPath: "."
        ),
        .target(
            name: "KMMViewModelCore",
            dependencies: [.target(name: "KMMViewModelCoreObjC")],
            path: "KMMViewModelCore"
        ),
        .target(
            name: "KMMViewModelSwiftUI",
            dependencies: [.target(name: "KMMViewModelCore")],
            path: "KMMViewModelSwiftUI"
        ),
        .target(
            name: "KMMViewModelState",
            dependencies: [
                .target(name: "KMMViewModelCore"),
                .target(name: "KMMViewModelSwiftUI")
            ],
            path: "KMMViewModelState"
        )
    ],
    swiftLanguageVersions: [.v5]
)
