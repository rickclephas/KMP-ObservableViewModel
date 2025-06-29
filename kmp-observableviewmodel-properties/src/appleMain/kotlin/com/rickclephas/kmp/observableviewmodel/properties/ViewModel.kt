package com.rickclephas.kmp.observableviewmodel.properties

import com.rickclephas.kmp.nativecoroutines.NativeFlow
import com.rickclephas.kmp.nativecoroutines.asNativeFlow
import com.rickclephas.kmp.observableviewmodel.InternalKMPObservableViewModelApi
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.ViewModelScope
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import com.rickclephas.kmp.observableviewmodel.keyPath
import com.rickclephas.kmp.observableviewmodel.objc.KMPOVMViewModelKeyPathProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.Foundation.NSThread
import kotlin.experimental.ExperimentalObjCName

/**
 * A Kotlin Multiplatform ViewModel.
 */
public actual abstract class ViewModel: ViewModel() {

    /**
     * Class that stores all observable properties of a ViewModel.
     */
    @InternalKMPObservableViewModelApi
    public abstract class Properties {

        private var registeringStateFlow: StateFlow<*>? = null

        /**
         * Indicates if the `KeyPath` of the accessed property should be registered.
         * @see registerKeyPath
         */
        public fun shouldRegisterKeyPath(): Boolean = registeringStateFlow != null

        /**
         * Registers the [keyPath] for the previously accessed property.
         * @see shouldRegisterKeyPath
         */
        @OptIn(ExperimentalObjCName::class)
        public fun registerKeyPath(@ObjCName(swiftName = "_") keyPath: KMPOVMViewModelKeyPathProtocol) {
            registeringStateFlow?.keyPath = keyPath
            registeringStateFlow = null
        }

        /**
         * Returns the value of the provided [stateFlow] and ensures `KeyPath`s are being registered.
         * @see shouldRegisterKeyPath
         */
        @HiddenFromObjC
        protected fun <T> getProperty(stateFlow: StateFlow<T>): T {
            if (stateFlow.keyPath == null && NSThread.isMainThread) {
                registeringStateFlow = stateFlow
            }
            return stateFlow.value
        }

        /**
         * Sets the value of the [stateFlow] to the provided [value].
         */
        @HiddenFromObjC
        protected fun <T> setProperty(stateFlow: MutableStateFlow<T>, value: T) {
            stateFlow.value = value
        }
    }

    /**
     * The observable properties of `this` ViewModel.
     */
    @ShouldRefineInSwift
    @InternalKMPObservableViewModelApi
    public abstract val properties: Properties

    /**
     * Class that provides [NativeFlow]s for all observable properties of a ViewModel.
     */
    public abstract class NativeFlows(
        viewModelScope: ViewModelScope
    ) {
        private val coroutineScope: CoroutineScope = viewModelScope.coroutineScope

        /**
         * Returns a [NativeFlow] for the provided [stateFlow] using the `viewModelScope`.
         */
        @HiddenFromObjC
        protected fun <T> getNativeFlow(stateFlow: StateFlow<T>): NativeFlow<T> =
            stateFlow.asNativeFlow(coroutineScope)
    }

    /**
     * The [NativeFlow]s for the observable [properties] of `this` ViewModel.
     */
    public abstract val nativeFlows: NativeFlows
}
