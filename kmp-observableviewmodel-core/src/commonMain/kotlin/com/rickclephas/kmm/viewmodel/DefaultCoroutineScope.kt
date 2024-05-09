package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Creates a default [CoroutineScope] for a ViewModel,
 * using the [Main.immediate][MainCoroutineDispatcher.immediate] dispatcher if available.
 *
 * [androidx/CloseableCoroutineScope.kt](https://cs.android.com/androidx/platform/frameworks/support/+/6a69101fd0edc8d02aa316df1f43e0552fd2d7c4:lifecycle/lifecycle-viewmodel/src/commonMain/kotlin/androidx/lifecycle/viewmodel/internal/CloseableCoroutineScope.kt;l=51-66)
 */
@Suppress("FunctionName")
internal fun DefaultCoroutineScope(): CoroutineScope {
    val dispatcher = try {
        Dispatchers.Main.immediate
    } catch (_: NotImplementedError) {
        EmptyCoroutineContext
    } catch (_: IllegalStateException) {
        EmptyCoroutineContext
    }
    return CoroutineScope(SupervisorJob() + dispatcher)
}
