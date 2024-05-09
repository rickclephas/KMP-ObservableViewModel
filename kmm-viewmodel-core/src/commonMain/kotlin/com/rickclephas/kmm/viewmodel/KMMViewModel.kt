package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public expect abstract class KMMViewModel {

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public val viewModelScope: ViewModelScope

    public constructor()

    public constructor(coroutineScope: CoroutineScope)

    @OptIn(ExperimentalStdlibApi::class)
    public constructor(vararg closeables: AutoCloseable)

    @OptIn(ExperimentalStdlibApi::class)
    public constructor(coroutineScope: CoroutineScope, vararg closeables: AutoCloseable)

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public open fun onCleared()
}

@OptIn(ExperimentalStdlibApi::class)
public expect fun KMMViewModel.addCloseable(key: String, closeable: AutoCloseable)

@OptIn(ExperimentalStdlibApi::class)
public expect fun KMMViewModel.addCloseable(closeable: AutoCloseable)

@OptIn(ExperimentalStdlibApi::class)
public expect fun <T : AutoCloseable> KMMViewModel.getCloseable(key: String): T?
