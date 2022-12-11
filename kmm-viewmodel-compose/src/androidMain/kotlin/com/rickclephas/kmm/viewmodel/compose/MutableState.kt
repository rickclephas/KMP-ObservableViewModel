package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias MutableState<T> = MutableState<T>

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T : Any?> mutableStateOf(
    viewModelScope: ViewModelScope,
    value: T,
    policy: SnapshotMutationPolicy<T>
): MutableState<T> = androidx.compose.runtime.mutableStateOf(value, policy)
