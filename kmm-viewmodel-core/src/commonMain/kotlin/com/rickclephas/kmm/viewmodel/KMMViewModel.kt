package com.rickclephas.kmm.viewmodel

import kotlinx.coroutines.CoroutineScope

/**
 * A Kotlin Multiplatform Mobile ViewModel.
 */
public expect abstract class KMMViewModel(coroutineScope: CoroutineScope) {

    public constructor()

    /**
     * The [ViewModelScope] containing the [CoroutineScope] of this ViewModel.
     */
    public val viewModelScope: ViewModelScope

    /**
     * Called when this ViewModel is no longer used and will be destroyed.
     */
    public open fun onCleared()
}
