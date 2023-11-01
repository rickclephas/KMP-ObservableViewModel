# KMM-ViewModel

A library that allows you to share ViewModels between Android and iOS.

## Compatibility

The latest version of the library uses Kotlin version `1.9.20`.  
Compatibility versions for older and/or preview Kotlin versions are also available:

| Version        | Version suffix       |    Kotlin    | Coroutines | AndroidX Lifecycle |
|----------------|----------------------|:------------:|:----------:|:------------------:|
| **_latest_**   | **_no suffix_**      |  **1.9.20**  | **1.7.3**  |     **2.6.2**      |
| 1.0.0-ALPHA-14 | _no suffix_          |    1.9.10    |   1.7.3    |       2.6.1        |
| 1.0.0-ALPHA-13 | _no suffix_          |    1.9.0     |   1.7.3    |       2.6.1        |
| 1.0.0-ALPHA-10 | _no suffix_          |    1.8.22    |   1.7.2    |       2.6.1        |
| 1.0.0-ALPHA-9  | _no suffix_          |    1.8.21    |   1.7.1    |       2.5.1        |
| 1.0.0-ALPHA-8  | _no suffix_          |    1.8.21    |   1.7.0    |       2.5.1        |
| 1.0.0-ALPHA-7  | _no suffix_          |    1.8.21    |   1.6.4    |       2.5.1        |
| 1.0.0-ALPHA-6  | _no suffix_          |    1.8.20    |   1.6.4    |       2.5.1        |
| 1.0.0-ALPHA-4  | _no suffix_          |    1.8.10    |   1.6.4    |       2.5.1        |
| 1.0.0-ALPHA-3  | _no suffix_          |    1.8.0     |   1.6.4    |       2.5.1        |

## Kotlin

Add the library to your shared Kotlin module and opt-in to the `ExperimentalForeignApi`:
```kotlin
kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        commonMain {
            dependencies {
                api("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-15")
            }
        }
    }
}
```

And create your ViewModels:
```kotlin
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.stateIn

open class TimeTravelViewModel: KMMViewModel() {

    private val clockTime = Clock.time

    /**
     * A [StateFlow] that emits the actual time.
     */
    val actualTime = clockTime.map { formatTime(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")

    private val _travelEffect = MutableStateFlow<TravelEffect?>(viewModelScope, null)
    /**
     * A [StateFlow] that emits the applied [TravelEffect].
     */
    val travelEffect = _travelEffect.asStateFlow()
}
```

As you might notice it isn't much different from an Android ViewModel.  
The most obvious difference is the `KMMViewModel` superclass:

```diff
- import androidx.lifecycle.ViewModel
+ import com.rickclephas.kmm.viewmodel.KMMViewModel

- open class TimeTravelViewModel: ViewModel() {
+ open class TimeTravelViewModel: KMMViewModel() {
```

Besides that there are only 2 minor differences.  
The first being a different import for `stateIn`:

```diff
- import kotlinx.coroutines.flow.stateIn
+ import com.rickclephas.kmm.viewmodel.stateIn

        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")
```

And the second being a different `MutableStateFlow` constructor:

```diff
- import kotlinx.coroutines.flow.MutableStateFlow
+ import com.rickclephas.kmm.viewmodel.MutableStateFlow

-    private val _travelEffect = MutableStateFlow<TravelEffect?>(null)
+    private val _travelEffect = MutableStateFlow<TravelEffect?>(viewModelScope, null)
```

These minor differences will make sure that any state changes are propagated to iOS.  

> [!NOTE]
> `viewModelScope` is a wrapper around the actual `CoroutineScope` which can be accessed 
> via the `ViewModelScope.coroutineScope` property.

### KMP-NativeCoroutines

I highly recommend you to use the `@NativeCoroutinesState` annotation from
[KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines)
to turn your `StateFlow`s into properties in Swift:

```kotlin
@NativeCoroutinesState
val travelEffect = _travelEffect.asStateFlow()
```

Checkout the KMP-NativeCoroutines [README](https://github.com/rickclephas/KMP-NativeCoroutines/blob/master/README.md)
for more information and installation instructions.

<details><summary>Alternative</summary>
<p>

Alternatively you can create extension properties in your iOS source-set yourself:
```kotlin
val TimeTravelViewModel.travelEffectValue: TravelEffect?
    get() = travelEffect.value
```
</p>
</details>

## Android

Use the view model like you would any other Android view model:
```kotlin
class TimeTravelFragment: Fragment(R.layout.fragment_time_travel) {
    private val viewModel: TimeTravelViewModel by viewModels()
}
```

> [!NOTE]
> Improved support for Jetpack Compose is coming soon.

## Swift

Add the Swift package to your `Package.swift` file:
```swift
dependencies: [
    .package(url: "https://github.com/rickclephas/KMM-ViewModel.git", from: "1.0.0-ALPHA-15")
]
```

Or add it in Xcode by going to `File` > `Add Packages...` and providing the URL:
`https://github.com/rickclephas/KMM-ViewModel.git`.

<details><summary>CocoaPods</summary>
<p>

If you like you can also use CocoaPods instead of SPM:
```ruby
pod 'KMMViewModelSwiftUI', '1.0.0-ALPHA-15'
```
</p>
</details>

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
import KMMViewModelSwiftUI
import shared // This should be your shared KMM module

struct ContentView: View {
    @StateViewModel var viewModel = TimeTravelViewModel()
}
```

It's also possible to subclass your view model in Swift:
```swift
import Combine
import shared // This should be your shared KMM module

class TimeTravelViewModel: shared.TimeTravelViewModel {
    @Published var isResetDisabled: Bool = false
}
```

### Child view models

You'll need some additional logic if your `KMMViewModel`s expose child view models.

First make sure to use the `NativeCoroutinesRefinedState` annotation instead of the `NativeCoroutinesState` annotation:
```kotlin
class MyParentViewModel: KMMViewModel() {
    @NativeCoroutinesRefinedState
    val myChildViewModel: StateFlow<MyChildViewModel?> = MutableStateFlow(null)
}
```

After that you should create a Swift extension property using the `childViewModel(at:)` function: 
```swift
extension MyParentViewModel {
    var myChildViewModel: MyChildViewModel? {
        childViewModel(at: \.__myChildViewModel)
    }
}
```

This will prevent your Swift view models from being deallocated too soon. 

> [!NOTE]
> For lists, sets and dictionaries containing view models there is `childViewModels(at:)`.
