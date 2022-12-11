package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope
import kotlin.reflect.KProperty

public expect interface MutableState<T>: State<T> {
    public override var value: T
    public operator fun component1(): T
    public operator fun component2(): (T) -> Unit
}

public expect fun <T : Any?> mutableStateOf(
    viewModelScope: ViewModelScope,
    value: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): MutableState<T>

public inline operator fun <T : Any?> MutableState<T>.setValue(
    thisObj: Any?,
    property: KProperty<*>,
    value: T
) { this.value = value }
