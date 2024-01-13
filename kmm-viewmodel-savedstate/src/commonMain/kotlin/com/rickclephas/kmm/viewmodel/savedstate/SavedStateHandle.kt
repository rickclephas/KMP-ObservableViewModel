package com.rickclephas.kmm.viewmodel.savedstate

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.flow.StateFlow

public expect class SavedStateHandle() {
    public fun keys(): Set<String>
    public operator fun contains(key: String): Boolean
    public inline fun <reified T> getStateFlow(viewModelScope: ViewModelScope, key: String, initialValue: T): StateFlow<T>
    public inline operator fun <reified T> get(key: String): T?
    public inline operator fun <reified T> set(key: String, value: T?)
    public inline fun <reified T> remove(key: String): T?
}
