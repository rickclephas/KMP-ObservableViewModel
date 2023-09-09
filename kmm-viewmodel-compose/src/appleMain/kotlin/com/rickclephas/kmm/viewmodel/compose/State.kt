package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.State as ComposeState
import kotlinx.coroutines.flow.StateFlow

public actual interface State<out T>: ComposeState<T>, StateFlow<T>
