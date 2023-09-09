package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.snapshots.SnapshotStateList as ComposeSnapshotStateList
import androidx.compose.runtime.mutableStateListOf as composeMutableStateListOf
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias SnapshotStateList<T> = ComposeSnapshotStateList<T>

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T> ViewModelScope.mutableStateListOf(): SnapshotStateList<T> =
    composeMutableStateListOf()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T> ViewModelScope.mutableStateListOf(
    vararg elements: T
): SnapshotStateList<T> = composeMutableStateListOf(*elements)
