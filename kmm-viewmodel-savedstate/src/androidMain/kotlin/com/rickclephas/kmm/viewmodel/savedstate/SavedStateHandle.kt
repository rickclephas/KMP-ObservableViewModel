package com.rickclephas.kmm.viewmodel.savedstate

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.serializer

public actual class SavedStateHandle(
    @PublishedApi internal val handle: AndroidSavedStateHandle
) {
    public actual constructor() : this(AndroidSavedStateHandle())
    public constructor(initialState: Map<String, Any?>) : this(AndroidSavedStateHandle(initialState))

    public actual fun keys(): Set<String> = handle.keys()

    public actual operator fun contains(key: String): Boolean = handle.contains(key)

    public actual inline fun <reified T> getStateFlow(
        viewModelScope: ViewModelScope,
        key: String,
        initialValue: T
    ): StateFlow<T> {
        if (!shouldSerialize(T::class)) return handle.getStateFlow(key, initialValue)
        val serializer = serializer<T>()
        val stateFlow = handle.getStateFlow(key, serializer.serialize(initialValue))
        return serializer.deserialize(stateFlow)
    }

    public actual inline operator fun <reified T> get(key: String): T? {
        if (!shouldSerialize(T::class)) return handle[key]
        val value: ByteArray = handle[key] ?: return null
        return serializer<T>().deserialize(value)
    }

    public actual inline operator fun <reified T> set(key: String, value: T?) {
        if (!shouldSerialize(T::class)) return handle.set(key, value)
        return handle.set(key, value?.let { serializer<T>().serialize(value) })
    }

    public actual inline fun <reified T> remove(key: String): T? {
        if (!shouldSerialize(T::class)) return handle.remove(key)
        val value = handle.remove<ByteArray>(key) ?: return null
        return serializer<T>().deserialize(value)
    }
}
