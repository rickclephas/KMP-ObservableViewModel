package com.rickclephas.kmm.viewmodel.savedstate

import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import platform.Foundation.NSData

public actual class SavedStateHandle actual constructor() {

    public constructor(configure: SavedStateHandle.() -> Unit): this() { configure() }

    private val serializedState = mutableMapOf<String, ByteArray?>()
    private val state = mutableMapOf<String, Any?>()
    private val flows = mutableMapOf<String, Pair<MutableStateFlow<Any?>, KSerializer<*>>>()

    private var stateChangedListener: ((NSData?) -> Unit)? = null

    public fun setStateChangedListener(listener: (NSData?) -> Unit) {
        stateChangedListener = listener
    }

    private fun invokeStateChangedListener() {
        stateChangedListener?.invoke(data)
    }

    public var data: NSData?
        get() = serializedState.serialize()
        set(value) {
            if (value != null) {
                value.deserialize().forEach { (key, value) -> serializedState[key] = value }
                state.clear()
                flows.forEach { (key, value) ->
                    val (flow, serializer) = value
                    flow.value = get(key, serializer)
                }
            }
            invokeStateChangedListener()
        }

    public actual fun keys(): Set<String> = serializedState.keys

    public actual operator fun contains(key: String): Boolean = serializedState.contains(key)

    @HiddenFromObjC
    public actual inline fun <reified T> getStateFlow(
        viewModelScope: ViewModelScope,
        key: String,
        initialValue: T
    ): StateFlow<T> = getStateFlow(viewModelScope, key, initialValue, serializer())

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T> getStateFlow(
        viewModelScope: ViewModelScope,
        key: String,
        initialValue: T,
        serializer: KSerializer<T>
    ): StateFlow<T> = flows.getOrPut(key) {
        val value = get(key, serializer) ?: initialValue.also { set(key, it, serializer) }
        Pair(MutableStateFlow(viewModelScope, value), serializer)
    }.first.asStateFlow() as StateFlow<T>

    @HiddenFromObjC
    public actual inline operator fun <reified T> get(key: String): T? = get(key, serializer())

    @PublishedApi
    @Suppress("UNCHECKED_CAST")
    internal fun <T> get(key: String, serializer: KSerializer<T>): T? =
        state.getOrPut(key) { serializedState[key]?.let(serializer::deserialize) } as T?

    @HiddenFromObjC
    public actual inline operator fun <reified T> set(key: String, value: T?): Unit = set(key, value, serializer())

    @PublishedApi
    internal fun <T> set(key: String, value: T?, serializer: KSerializer<T>) {
        state[key] = value
        serializedState[key] = value?.let(serializer::serialize)
        flows[key]?.first?.value = value
        invokeStateChangedListener()
    }

    @HiddenFromObjC
    public actual inline fun <reified T> remove(key: String): T? = remove(key, serializer())

    @PublishedApi
    internal fun <T> remove(key: String, serializer: KSerializer<T>): T? {
        val value = state.remove(key)
        val serializedValue = serializedState.remove(key)
        flows.remove(key)
        invokeStateChangedListener()
        @Suppress("UNCHECKED_CAST")
        return value as T? ?: serializedValue?.let(serializer::deserialize)
    }
}
