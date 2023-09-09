package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope

public expect class SnapshotStateList<T>: MutableList<T> {
    public fun toList(): List<T>
    public fun removeRange(fromIndex: Int, toIndex: Int)
}

public expect fun <T> ViewModelScope.mutableStateListOf(): SnapshotStateList<T>

public expect fun <T> ViewModelScope.mutableStateListOf(vararg elements: T): SnapshotStateList<T>
