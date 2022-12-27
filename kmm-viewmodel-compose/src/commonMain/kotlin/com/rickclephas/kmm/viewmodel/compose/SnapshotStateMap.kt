package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope

public expect class SnapshotStateMap<K, V>: MutableMap<K, V>

public expect fun <K, V> mutableStateMapOf(viewModelScope: ViewModelScope): SnapshotStateMap<K, V>

public expect fun <K, V> mutableStateMapOf(
    viewModelScope: ViewModelScope,
    vararg pairs: Pair<K, V>
): SnapshotStateMap<K, V>
