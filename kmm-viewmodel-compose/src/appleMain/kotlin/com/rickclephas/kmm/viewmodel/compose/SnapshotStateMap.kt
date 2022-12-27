package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public actual class SnapshotStateMap<K, V> internal constructor(
    private val stateFlow: MutableStateFlow<PersistentMap<K, V>>
): MutableMap<K, V>, StateFlow<Map<K, V>> by stateFlow {

    @Suppress("DuplicatedCode")
    private inline fun <R> mutate(function: MutableMap<K, V>.() -> R): R {
        while (true) {
            val prevValue = stateFlow.value
            val builder = prevValue.builder()
            val result = function(builder)
            val nextValue = builder.build()
            if (nextValue == prevValue || stateFlow.compareAndSet(prevValue, nextValue)) {
                return result
            }
        }
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = TODO("Not yet implemented")
    override val keys: MutableSet<K>
        get() = TODO("Not yet implemented")
    override val size: Int get() = value.size
    override val values: MutableCollection<V>
        get() = TODO("Not yet implemented")
    override fun clear(): Unit = mutate { clear() }
    override fun containsKey(key: K): Boolean = value.containsKey(key)
    override fun containsValue(value: V): Boolean = this.value.containsValue(value)
    override fun get(key: K): V? = value[key]
    override fun isEmpty(): Boolean = values.isEmpty()
    override fun put(key: K, value: V): V? = mutate { put(key, value) }
    override fun putAll(from: Map<out K, V>): Unit = mutate { putAll(from) }
    override fun remove(key: K): V? = mutate { remove(key) }
}

public actual fun <K, V> mutableStateMapOf(viewModelScope: ViewModelScope): SnapshotStateMap<K, V> =
    SnapshotStateMap(MutableStateFlow(viewModelScope, persistentMapOf()))

public actual fun <K, V> mutableStateMapOf(
    viewModelScope: ViewModelScope,
    vararg pairs: Pair<K, V>
): SnapshotStateMap<K, V> = SnapshotStateMap(MutableStateFlow(viewModelScope, persistentMapOf(*pairs)))
