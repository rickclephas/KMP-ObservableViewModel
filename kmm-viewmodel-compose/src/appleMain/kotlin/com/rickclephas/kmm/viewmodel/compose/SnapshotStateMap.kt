package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.snapshots.SnapshotStateMap as ComposeSnapshotStateMap
import androidx.compose.runtime.mutableStateMapOf as composeMutableStateMapOf
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public actual class SnapshotStateMap<K, V> internal constructor(
    private val stateMap: ComposeSnapshotStateMap<K, V>,
    private val stateFlow: MutableStateFlow<Map<K, V>>
): MutableMap<K, V>, Map<K, V> by stateMap, StateFlow<Map<K, V>> by stateFlow {

    private inline fun <R> read(block: ComposeSnapshotStateMap<K, V>.() -> R): R = stateMap.block()
    private inline fun <R> modify(block: ComposeSnapshotStateMap<K, V>.() -> R): R {
        val result = stateMap.block()
        stateFlow.value = stateMap.toMap()
        return result
    }

    //region SnapshotStateMap
    public actual fun toMap(): Map<K, V> = read { toMap() }
    //endregion

    //region MutableMap
    override fun clear(): Unit = modify { clear() }
    override fun put(key: K, value: V): V? = modify { put(key, value) }
    override fun putAll(from: Map<out K, V>): Unit = modify { putAll(from) }
    override fun remove(key: K): V? = modify { remove(key) }
    //endregion

    //region EntrySet
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = EntrySet(this)
    private class EntrySet<K, V>(private val map: SnapshotStateMap<K, V>): MutableSet<MutableMap.MutableEntry<K, V>> {

        private inline fun <R> read(block: MutableSet<MutableMap.MutableEntry<K, V>>.() -> R): R =
            map.read { entries.block() }
        private inline fun <R> modify(block: MutableSet<MutableMap.MutableEntry<K, V>>.() -> R): R =
            map.modify { entries.block() }

        override fun add(element: MutableMap.MutableEntry<K, V>): Boolean = modify { add(element) }
        override fun addAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean = modify { addAll(elements) }
        override val size: Int get() = read { size }
        override fun clear(): Unit = modify { clear() }
        override fun isEmpty(): Boolean = read { isEmpty() }
        override fun containsAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean =
            read { containsAll(elements) }
        override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean = read { contains(element) }
        @Suppress("ConvertArgumentToSet")
        override fun retainAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean =
            modify { retainAll(elements) }
        @Suppress("ConvertArgumentToSet")
        override fun removeAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean =
            modify { removeAll(elements) }
        override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean = modify { remove(element) }
        override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> {
            val iterator = read { iterator() }
            return object : MutableIterator<MutableMap.MutableEntry<K, V>> {
                override fun hasNext(): Boolean = iterator.hasNext()
                override fun remove(): Unit = modify { iterator.remove() }
                override fun next(): MutableMap.MutableEntry<K, V> {
                    val entry = iterator.next()
                    return object : MutableMap.MutableEntry<K, V>, Map.Entry<K, V> by entry {
                        override fun setValue(newValue: V): V = modify { entry.setValue(newValue) }
                    }
                }
            }
        }
    }
    //endregion

    //region KeySet
    override val keys: MutableSet<K> = KeySet(this)
    private class KeySet<K, V>(private val map: SnapshotStateMap<K, V>): MutableSet<K> {

        private inline fun <R> read(block: MutableSet<K>.() -> R): R = map.read { keys.block() }
        private inline fun <R> modify(block: MutableSet<K>.() -> R): R = map.modify { keys.block() }

        override fun add(element: K): Boolean = modify { add(element) }
        override fun addAll(elements: Collection<K>): Boolean = modify { addAll(elements) }
        override val size: Int get() = read { size }
        override fun clear(): Unit = modify { clear() }
        override fun isEmpty(): Boolean = read { isEmpty() }
        override fun containsAll(elements: Collection<K>): Boolean = read { containsAll(elements) }
        override fun contains(element: K): Boolean = read { contains(element) }
        @Suppress("ConvertArgumentToSet")
        override fun retainAll(elements: Collection<K>): Boolean = modify { retainAll(elements) }
        @Suppress("ConvertArgumentToSet")
        override fun removeAll(elements: Collection<K>): Boolean = modify { removeAll(elements) }
        override fun remove(element: K): Boolean = modify { remove(element) }
        override fun iterator(): MutableIterator<K> {
            val iterator = read { iterator() }
            return object : MutableIterator<K> {
                override fun hasNext(): Boolean = iterator.hasNext()
                override fun next(): K = iterator.next()
                override fun remove(): Unit = modify { iterator.remove() }
            }
        }
    }
    //endregion

    //region ValueCollection
    override val values: MutableCollection<V> = ValueCollection(this)
    private class ValueCollection<K, V>(private val map: SnapshotStateMap<K, V>): MutableCollection<V> {

        private inline fun <R> read(block: MutableCollection<V>.() -> R): R = map.read { values.block() }
        private inline fun <R> modify(block: MutableCollection<V>.() -> R): R = map.modify { values.block() }

        override val size: Int get() = read { size }
        override fun clear(): Unit = modify { clear() }
        override fun addAll(elements: Collection<V>): Boolean = modify { addAll(elements) }
        override fun add(element: V): Boolean = modify { add(element) }
        override fun isEmpty(): Boolean = read { isEmpty() }
        @Suppress("ConvertArgumentToSet")
        override fun retainAll(elements: Collection<V>): Boolean = modify { retainAll(elements) }
        @Suppress("ConvertArgumentToSet")
        override fun removeAll(elements: Collection<V>): Boolean = modify { removeAll(elements) }
        override fun remove(element: V): Boolean = modify { remove(element) }
        override fun containsAll(elements: Collection<V>): Boolean = read { containsAll(elements) }
        override fun contains(element: V): Boolean = read { contains(element) }
        override fun iterator(): MutableIterator<V> {
            val iterator = read { iterator() }
            return object : MutableIterator<V> {
                override fun hasNext(): Boolean = iterator.hasNext()
                override fun next(): V = iterator.next()
                override fun remove() = modify { iterator.remove() }
            }
        }
    }
    //endregion
}

public actual fun <K, V> ViewModelScope.mutableStateMapOf(): SnapshotStateMap<K, V> {
    val stateMap = composeMutableStateMapOf<K, V>()
    val stateFlow = MutableStateFlow(this, stateMap.toMap())
    return SnapshotStateMap(stateMap, stateFlow)
}

public actual fun <K, V> ViewModelScope.mutableStateMapOf(vararg pairs: Pair<K, V>): SnapshotStateMap<K, V> {
    val stateMap = composeMutableStateMapOf(*pairs)
    val stateFlow = MutableStateFlow(this, stateMap.toMap())
    return SnapshotStateMap(stateMap, stateFlow)
}
