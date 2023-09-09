package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.snapshots.SnapshotStateList as CombineSnapshotStateList
import androidx.compose.runtime.mutableStateListOf as composeMutableStateListOf
import com.rickclephas.kmm.viewmodel.ViewModelScope
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public actual class SnapshotStateList<T> internal constructor(
    private val stateList: CombineSnapshotStateList<T>,
    private val stateFlow: MutableStateFlow<List<T>>
): MutableList<T>, List<T> by stateList, StateFlow<List<T>> by stateFlow {

    private inline fun <R> read(block: CombineSnapshotStateList<T>.() -> R): R = stateList.block()
    private inline fun <R> modify(block: CombineSnapshotStateList<T>.() -> R): R {
        val result = stateList.block()
        stateFlow.value = stateList.toList()
        return result
    }

    //region SnapshotStateList
    public actual fun toList(): List<T> = read { toList() }
    public actual fun removeRange(fromIndex: Int, toIndex: Int): Unit = modify { removeRange(fromIndex, toIndex) }
    //endregion

    //region MutableList
    override fun add(element: T): Boolean = modify { add(element) }
    override fun add(index: Int, element: T): Unit = modify { add(index, element) }
    override fun addAll(index: Int, elements: Collection<T>): Boolean = modify { addAll(index, elements) }
    override fun addAll(elements: Collection<T>): Boolean = modify { addAll(elements) }
    override fun clear(): Unit = modify { clear() }
    override fun remove(element: T): Boolean = modify { remove(element) }
    override fun removeAll(elements: Collection<T>): Boolean = modify { removeAll(elements) }
    override fun removeAt(index: Int): T = modify { removeAt(index) }
    override fun retainAll(elements: Collection<T>): Boolean = modify { retainAll(elements) }
    override fun set(index: Int, element: T): T = modify { set(index, element) }
    //endregion

    //region ListIterator
    override fun iterator(): MutableIterator<T> = listIterator(0)
    override fun listIterator(): MutableListIterator<T> = listIterator(0)
    override fun listIterator(index: Int): MutableListIterator<T> = read {
        ListIterator(this@SnapshotStateList, listIterator(index))
    }
    private class ListIterator<T>(
        private val list: SnapshotStateList<T>,
        private val iterator: MutableListIterator<T>
    ): MutableListIterator<T> {

        private inline fun <R> read(block: MutableListIterator<T>.() -> R): R = list.read { iterator.block() }
        private inline fun <R> modify(block: MutableListIterator<T>.() -> R): R = list.modify { iterator.block() }

        override fun add(element: T): Unit = modify { add(element) }
        override fun hasNext(): Boolean = read { hasNext() }
        override fun hasPrevious(): Boolean = read { hasNext() }
        override fun next(): T = read { next() }
        override fun nextIndex(): Int = read { nextIndex() }
        override fun previous(): T = read { previous() }
        override fun previousIndex(): Int = read { previousIndex() }
        override fun remove(): Unit = modify { remove() }
        override fun set(element: T): Unit = modify { set(element) }
    }
    //endregion

    //region SubList
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = read {
        SubList(this@SnapshotStateList, subList(fromIndex, toIndex))
    }
    private class SubList<T>(
        private val list: SnapshotStateList<T>,
        private val subList: MutableList<T>
    ): MutableList<T> {

        private inline fun <R> read(block: MutableList<T>.() -> R): R = list.read { subList.block() }
        private inline fun <R> modify(block: MutableList<T>.() -> R): R = list.modify { subList.block() }

        override val size: Int get() = read { size }
        override fun clear(): Unit = modify { clear() }
        override fun addAll(elements: Collection<T>): Boolean = modify { addAll(elements) }
        override fun addAll(index: Int, elements: Collection<T>): Boolean = modify { addAll(index, elements) }
        override fun add(index: Int, element: T): Unit = modify { add(index, element) }
        override fun add(element: T): Boolean = modify { add(element) }
        override fun get(index: Int): T = read { get(index) }
        override fun isEmpty(): Boolean = read { isEmpty() }
        override fun removeAt(index: Int): T = modify { removeAt(index) }
        override fun set(index: Int, element: T): T = modify { set(index, element) }
        override fun retainAll(elements: Collection<T>): Boolean = modify { retainAll(elements) }
        override fun removeAll(elements: Collection<T>): Boolean = modify { removeAll(elements) }
        override fun remove(element: T): Boolean = modify { remove(element) }
        override fun lastIndexOf(element: T): Int = read { lastIndexOf(element) }
        override fun indexOf(element: T): Int = read { indexOf(element) }
        override fun containsAll(elements: Collection<T>): Boolean = read { containsAll(elements) }
        override fun contains(element: T): Boolean = read { contains(element) }
        override fun iterator(): MutableIterator<T> = listIterator(0)
        override fun listIterator(): MutableListIterator<T> = listIterator(0)
        override fun listIterator(index: Int): MutableListIterator<T> = read {
            ListIterator(list, listIterator(index))
        }
        override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = read {
            SubList(list, subList(fromIndex, toIndex))
        }
    }
    //endregion
}

public actual fun <T> ViewModelScope.mutableStateListOf(): SnapshotStateList<T> {
    val stateList = composeMutableStateListOf<T>()
    val stateFlow = MutableStateFlow(this, stateList.toList())
    return SnapshotStateList(stateList, stateFlow)
}

public actual fun <T> ViewModelScope.mutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    val stateList = composeMutableStateListOf(*elements)
    val stateFlow = MutableStateFlow(this, stateList.toList())
    return SnapshotStateList(stateList, stateFlow)
}
