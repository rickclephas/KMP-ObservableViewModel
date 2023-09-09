package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.snapshots.SnapshotStateMap as ComposeSnapshotStateMap
import androidx.compose.runtime.mutableStateMapOf as composeMutableStateMapOf
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias SnapshotStateMap<K, V> = ComposeSnapshotStateMap<K, V>

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <K, V> ViewModelScope.mutableStateMapOf(): SnapshotStateMap<K, V> =
    composeMutableStateMapOf()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <K, V> ViewModelScope.mutableStateMapOf(
    vararg pairs: Pair<K, V>
): SnapshotStateMap<K, V> = composeMutableStateMapOf(*pairs)
