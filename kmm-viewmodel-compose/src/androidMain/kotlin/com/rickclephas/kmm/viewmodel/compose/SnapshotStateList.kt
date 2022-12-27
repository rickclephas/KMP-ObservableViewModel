package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.mutableStateListOf as mutableStateListOfImpl
import androidx.compose.runtime.snapshots.SnapshotStateList as SnapshotStateListImpl
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias SnapshotStateList<T> = SnapshotStateListImpl<T>

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T> mutableStateListOf(
    viewModelScope: ViewModelScope
): SnapshotStateList<T> = mutableStateListOfImpl()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T> mutableStateListOf(
    viewModelScope: ViewModelScope,
    vararg elements: T
): SnapshotStateList<T> = mutableStateListOfImpl(*elements)
