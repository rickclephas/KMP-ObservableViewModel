package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.mutableStateMapOf as mutableStateMapOfImpl
import androidx.compose.runtime.snapshots.SnapshotStateMap as SnapshotStateMapImpl
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias SnapshotStateMap<K, V> = SnapshotStateMapImpl<K, V>

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <K, V> mutableStateMapOf(viewModelScope: ViewModelScope): SnapshotStateMap<K, V> =
    mutableStateMapOfImpl()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <K, V> mutableStateMapOf(
    viewModelScope: ViewModelScope,
    vararg pairs: Pair<K, V>
): SnapshotStateMap<K, V> = mutableStateMapOfImpl(*pairs)
