package com.rickclephas.kmm.viewmodel.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.produceState as produceStateImpl
import androidx.compose.runtime.ProduceStateScope as ProduceStateScopeImpl
import com.rickclephas.kmm.viewmodel.ViewModelScope

public actual typealias ProduceStateScope<T> = ProduceStateScopeImpl<T>

@Suppress("NOTHING_TO_INLINE")
@SuppressLint("ProduceStateDoesNotAssignValue")
public actual inline fun <T> produceState(
    viewModelScope: ViewModelScope,
    initialValue: T,
    noinline producer: suspend ProduceStateScope<T>.() -> Unit
): State<T> = produceStateImpl(initialValue, producer)
