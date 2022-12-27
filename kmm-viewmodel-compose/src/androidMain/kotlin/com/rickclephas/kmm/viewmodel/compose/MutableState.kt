package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.MutableState as MutableStateImpl
import androidx.compose.runtime.mutableStateOf as mutableStateOfImpl
import androidx.compose.runtime.SnapshotMutationPolicy
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias MutableState<T> = MutableStateImpl<T>

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T : Any?> mutableStateOf(
    viewModelScope: ViewModelScope,
    value: T,
    policy: SnapshotMutationPolicy<T>
): MutableState<T> = mutableStateOfImpl(value, policy)
