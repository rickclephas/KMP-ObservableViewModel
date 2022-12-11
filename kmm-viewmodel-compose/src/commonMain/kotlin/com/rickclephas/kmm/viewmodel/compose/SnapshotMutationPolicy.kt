package com.rickclephas.kmm.viewmodel.compose

public expect interface SnapshotMutationPolicy<T>

public expect fun <T> structuralEqualityPolicy(): SnapshotMutationPolicy<T>
