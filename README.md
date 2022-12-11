# KMM-ViewModel

A library that allows you to share ViewModels between Android and iOS.

> **Warning**: this is still a WIP. Initial release coming soon 😁

## Kotlin

Add the library to your shared Kotlin module:
```kotlin
dependencies {
    implementation("com.rickclephas.kmm:kmm-viewmodel-core:<version>")
}
```

Create your ViewModels almost as you would in Android:
```kotlin
// 1: use KMMViewModel instead of ViewModel
open class TimeTravelViewModel: KMMViewModel() {

    private val clockTime = Clock.time

    /**
     * A [StateFlow] that emits the actual time.
     */
    val actualTime = clockTime.map { formatTime(it) }
        // 2: supply viewModelScope to your stateIn calls
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")

    // 3: supply viewModelScope to your MutableStateFlows
    private val _travelEffect = MutableStateFlow<TravelEffect?>(viewModelScope, null)
    /**
     * A [StateFlow] that emits the applied [TravelEffect].
     */
    val travelEffect = _travelEffect.asStateFlow()
}
```

You need to use `viewModelScope` wherever possible to propagate state changes to iOS.  
In other cases you can access the `ViewModelScope.coroutineScope` property directly.

## Android

Add the library to your Android module:
```kotlin
dependencies {
    implementation("com.rickclephas.kmm:kmm-viewmodel-core:<version>")
}
```

Use the view model like you would any other Android view model:
```kotlin
class TimeTravelFragment: Fragment(R.layout.fragment_time_travel) {
    private val viewModel: TimeTravelViewModel by viewModels()
}
```

> **Note:** support for Jetpack Compose is coming soon.


## Swift

Add the Swift package to your project:
```swift
dependencies: [
    .package(url: "https://github.com/rickclephas/KMM-ViewModel.git", from: "<version>")
]
```

Create a `KMMViewModel.swift` file with the following contents:
```swift
import KMMViewModelCore
import shared // This should be your shared KMM module

extension Kmm_viewmodel_coreKMMViewModel: KMMViewModel { }
```

After that you can use your view model almost as if it were an `ObservableObject`.   
Just use the view model specific property wrappers and functions:

| `ObservableObject`      | `KMMViewModel`             |
|-------------------------|----------------------------|
| `@StateObject`          | `@StateViewModel`          |
| `@ObservedObject`       | `@ObservedViewModel`       |
| `@EnvironmentObject`    | `@EnvironmentViewModel`    |
| `environmentObject(_:)` | `environmentViewModel(_:)` |

E.g. to use the `TimeTravelViewModel` as a `StateObject`:
```swift
import SwiftUI
import shared // This should be your shared KMM module

struct ContentView: View {
    @StateViewModel var viewModel = TimeTravelViewModel()
}
```

It's also possible to subclass your view model in Swift:
```swift
import shared // This should be your shared KMM module

class TimeTravelViewModel: shared.TimeTravelViewModel {
    @Published var isResetDisabled: Bool = false
}
```
