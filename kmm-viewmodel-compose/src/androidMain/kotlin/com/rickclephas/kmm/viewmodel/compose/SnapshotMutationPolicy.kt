package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.SnapshotMutationPolicy

public actual typealias SnapshotMutationPolicy<T> = SnapshotMutationPolicy<T>

public actual fun <T> structuralEqualityPolicy(): SnapshotMutationPolicy<T> =
    androidx.compose.runtime.structuralEqualityPolicy()
