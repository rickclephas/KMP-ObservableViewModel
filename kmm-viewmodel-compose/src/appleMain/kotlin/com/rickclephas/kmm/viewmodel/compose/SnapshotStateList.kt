package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public actual class SnapshotStateList<T> internal constructor(
    private val stateFlow: MutableStateFlow<PersistentList<T>>,
): MutableList<T>, StateFlow<List<T>> by stateFlow {

    @Suppress("DuplicatedCode")
    private inline fun <R> mutate(function: MutableList<T>.() -> R): R {
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

    override val size: Int get() = value.size
    override fun add(element: T): Boolean = mutate { add(element) }
    override fun add(index: Int, element: T): Unit = mutate { add(index, element) }
    override fun addAll(elements: Collection<T>): Boolean = mutate { addAll(elements) }
    override fun addAll(index: Int, elements: Collection<T>): Boolean = mutate { addAll(index, elements) }
    override fun clear(): Unit = mutate { clear() }
    override fun contains(element: T): Boolean = value.contains(element)
    override fun containsAll(elements: Collection<T>): Boolean = value.containsAll(elements)
    override fun get(index: Int): T = value[index]
    override fun indexOf(element: T): Int = value.indexOf(element)
    override fun isEmpty(): Boolean = value.isEmpty()
    override fun iterator(): MutableIterator<T> = listIterator()
    override fun lastIndexOf(element: T): Int = value.lastIndexOf(element)
    override fun listIterator(): MutableListIterator<T> = listIterator(0)
    override fun listIterator(index: Int): MutableListIterator<T> = TODO("Not yet implemented")
    override fun remove(element: T): Boolean = mutate { remove(element) }
    override fun removeAll(elements: Collection<T>): Boolean = mutate { removeAll(elements) }
    override fun removeAt(index: Int): T = mutate { removeAt(index) }
    override fun retainAll(elements: Collection<T>): Boolean = mutate { retainAll(elements) }
    override fun set(index: Int, element: T): T = mutate { set(index, element) }
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = TODO("Not yet implemented")
}

public actual fun <T> mutableStateListOf(viewModelScope: ViewModelScope): SnapshotStateList<T> =
    SnapshotStateList(MutableStateFlow(viewModelScope, persistentListOf()))

public actual fun <T> mutableStateListOf(viewModelScope: ViewModelScope, vararg elements: T): SnapshotStateList<T> =
    SnapshotStateList(MutableStateFlow(viewModelScope, persistentListOf(*elements)))
