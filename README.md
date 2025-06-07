# KMP-ObservableViewModel

A library (previously known as KMM-ViewModel) that allows you to use AndroidX/Kotlin ViewModels with SwiftUI.

## Compatibility

You can use this library in any KMP project,
but not all targets support AndroidX and/or SwiftUI interop:

| Target     |  Supported  | AndroidX | SwiftUI |
|------------|:-----------:|:--------:|:-------:|
| Android    |      ✅      |    ✅     |    -    |
| JVM        |      ✅      |    ✅     |    -    |
| iOS        |      ✅      |    ✅     |    ✅    |
| macOS      |      ✅      |    ✅     |    ✅    |
| tvOS       |      ✅      |    -     |    ✅    |
| watchOS    |      ✅      |    -     |    ✅    |
| linuxX64   |      ✅      |    ✅     |    -    |
| linuxArm64 |      ✅      |    -     |    -    |
| mingwX64   |      ✅      |    -     |    -    |
| JS         |      ✅      |    -     |    -    |
| Wasm       |      ✅      |    -     |    -    |

The latest version of the library uses Kotlin version `2.1.21`.  
Compatibility versions for older and/or preview Kotlin versions are also available:

| Version       | Version suffix      |   Kotlin    | Coroutines | AndroidX Lifecycle |
|---------------|---------------------|:-----------:|:----------:|:------------------:|
| _latest_      | -kotlin-2.2.0-RC    | 2.2.0-RC    |   1.10.1   |       2.8.7        |
| **_latest_**  | **_no suffix_**     | **2.1.21**  | **1.10.1** |     **2.8.7**      |
| 1.0.0-BETA-10 | _no suffix_         |   2.1.20    |   1.10.1   |       2.8.7        |
| 1.0.0-BETA-9  | _no suffix_         |   2.1.10    |   1.10.1   |       2.8.7        |
| 1.0.0-BETA-8  | _no suffix_         |    2.1.0    |   1.9.0    |       2.8.4        |
| 1.0.0-BETA-7  | _no suffix_         |   2.0.21    |   1.9.0    |       2.8.4        |
| 1.0.0-BETA-6  | _no suffix_         |   2.0.20    |   1.9.0    |       2.8.4        |
| 1.0.0-BETA-4  | _no suffix_         |   2.0.10    |   1.8.1    |       2.8.4        |
| 1.0.0-BETA-3  | _no suffix_         |    2.0.0    |   1.8.1    |       2.8.0        |
| 1.0.0-BETA-2  | _no suffix_         |   1.9.24    |   1.8.1    |       2.8.0        |

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
                api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-11")
            }
        }
    }
}
```

And create your ViewModels:
```kotlin
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.stateIn

open class TimeTravelViewModel: ViewModel() {

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

As you might notice it isn't much different from an AndroidX ViewModel.  
We are obviously using a different `ViewModel` superclass:

```diff
- import androidx.lifecycle.ViewModel
+ import com.rickclephas.kmp.observableviewmodel.ViewModel

open class TimeTravelViewModel: ViewModel() {
```

But besides that there are only 2 minor differences.  
The first being a different import for `stateIn`:

```diff
- import kotlinx.coroutines.flow.stateIn
+ import com.rickclephas.kmp.observableviewmodel.stateIn

        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")
```

And the second being a different `MutableStateFlow` constructor:

```diff
- import kotlinx.coroutines.flow.MutableStateFlow
+ import com.rickclephas.kmp.observableviewmodel.MutableStateFlow

-    private val _travelEffect = MutableStateFlow<TravelEffect?>(null)
+    private val _travelEffect = MutableStateFlow<TravelEffect?>(viewModelScope, null)
```

These minor differences will make sure that state changes are propagated to SwiftUI.  

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

Alternatively you can create extension properties in your iOS/Apple source-set yourself:
```kotlin
val TimeTravelViewModel.travelEffectValue: TravelEffect?
    get() = travelEffect.value
```
</p>
</details>

## Android

Use the view model like you would any other AndroidX ViewModel:
```kotlin
class TimeTravelFragment: Fragment(R.layout.fragment_time_travel) {
    private val viewModel: TimeTravelViewModel by viewModels()
}
```

## Swift

After you have configured your `shared` Kotlin module and created a ViewModel it's time to configure your Swift project.  
Start by adding the Swift package to your `Package.swift` file:
```swift
dependencies: [
    .package(url: "https://github.com/rickclephas/KMP-ObservableViewModel.git", from: "1.0.0-BETA-11")
]
```

Or add it in Xcode by going to `File` > `Add Packages...` and providing the URL:
`https://github.com/rickclephas/KMP-ObservableViewModel.git`.

<details><summary>CocoaPods</summary>
<p>

If you like you can also use CocoaPods instead of SPM:
```ruby
pod 'KMPObservableViewModelSwiftUI', '1.0.0-BETA-11'
```
</p>
</details>

Create a `KMPObservableViewModel.swift` file with the following contents:
```swift
import KMPObservableViewModelCore
import shared // This should be your shared KMP module

extension Kmp_observableviewmodel_coreViewModel: ViewModel { }
```

After that you can use your view model almost as if it were an `ObservableObject`.   
Just use the view model specific property wrappers and functions:

| `ObservableObject`      | `ViewModel`                |
|-------------------------|----------------------------|
| `@StateObject`          | `@StateViewModel`          |
| `@ObservedObject`       | `@ObservedViewModel`       |
| `@EnvironmentObject`    | `@EnvironmentViewModel`    |
| `environmentObject(_:)` | `environmentViewModel(_:)` |

E.g. to use the `TimeTravelViewModel` as a `StateObject`:
```swift
import SwiftUI
import KMPObservableViewModelSwiftUI
import shared // This should be your shared KMP module

struct ContentView: View {
    @StateViewModel var viewModel = TimeTravelViewModel()
}
```

It's also possible to subclass your view model in Swift:
```swift
import Combine
import shared // This should be your shared KMP module

class TimeTravelViewModel: shared.TimeTravelViewModel {
    @Published var isResetDisabled: Bool = false
}
```

### Child view models

You'll need some additional logic if your `ViewModel`s expose child view models.

First make sure to use the `NativeCoroutinesRefinedState` annotation instead of the `NativeCoroutinesState` annotation:
```kotlin
class MyParentViewModel: ViewModel() {
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

### Cancellable ViewModel

When subclassing your Kotlin ViewModel in Swift you might experience some issues in the way those view models are cleared.

An example of such an issue is when you are using a Combine publisher to observe a Flow through KMP-NativeCoroutines:
```swift
import Combine
import KMPNativeCoroutinesCombine
import shared // This should be your shared KMP module

class TimeTravelViewModel: shared.TimeTravelViewModel {

    private var cancellables = Set<AnyCancellable>()

    override init() {
        super.init()
        createPublisher(for: currentTimeFlow)
            .assertNoFailure()
            .sink { time in print("It's \(time)") }
            .store(in: &cancellables)
    }
}
```

Since `currentTimeFlow` is a StateFlow we don't ever expect it to fail, which is why we are using the `assertNoFailure`.
However, in this case you'll notice that the publisher will fail with a `JobCancellationException`.

The problem here is that before the `TimeTravelViewModel` is deinited it will already be cleared.
Meaning the `viewModelScope` is cancelled and `onCleared` is called.
This results in the Combine publisher outliving the underlying StateFlow collection.

To solve such issues you should have your Swift view model conform to `Cancellable` 
and perform the required cleanup in the `cancel` function:
```swift
class TimeTravelViewModel: shared.TimeTravelViewModel, Cancellable {
    func cancel() {
        cancellables = []
    }
}
```

KMP-ObservableViewModel will make sure to call the `cancel` function before the ViewModel is being cleared.
