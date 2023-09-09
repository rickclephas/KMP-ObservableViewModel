package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.MutableState as ComposeMutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.structuralEqualityPolicy
import com.rickclephas.kmm.viewmodel.ViewModelScope

public expect interface MutableState<T>: State<T>, ComposeMutableState<T>

public expect fun <T : Any?> ViewModelScope.mutableStateOf(
    value: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): MutableState<T>
