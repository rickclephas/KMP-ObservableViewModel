package com.rickclephas.kmm.viewmodel.compose

import kotlinx.coroutines.flow.StateFlow

public actual interface State<out T>: StateFlow<T> {
    public actual override val value: T
}
