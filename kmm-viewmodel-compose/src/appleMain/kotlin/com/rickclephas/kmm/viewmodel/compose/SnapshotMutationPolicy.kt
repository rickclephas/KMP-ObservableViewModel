package com.rickclephas.kmm.viewmodel.compose

public actual interface SnapshotMutationPolicy<T> {
    public fun equivalent(a: T, b: T): Boolean
}

@Suppress("UNCHECKED_CAST")
public actual fun <T> structuralEqualityPolicy(): SnapshotMutationPolicy<T> =
    StructuralEqualityPolicy as SnapshotMutationPolicy<T>

private object StructuralEqualityPolicy : SnapshotMutationPolicy<Any?> {
    override fun equivalent(a: Any?, b: Any?) = a == b
    override fun toString() = "StructuralEqualityPolicy"
}
