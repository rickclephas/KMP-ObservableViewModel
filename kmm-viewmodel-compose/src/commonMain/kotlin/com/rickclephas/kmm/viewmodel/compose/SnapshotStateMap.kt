package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope

public expect class SnapshotStateMap<K, V>: MutableMap<K, V> {
    public fun toMap(): Map<K, V>
}

public expect fun <K, V> ViewModelScope.mutableStateMapOf(): SnapshotStateMap<K, V>

public expect fun <K, V> ViewModelScope.mutableStateMapOf(vararg pairs: Pair<K, V>): SnapshotStateMap<K, V>
