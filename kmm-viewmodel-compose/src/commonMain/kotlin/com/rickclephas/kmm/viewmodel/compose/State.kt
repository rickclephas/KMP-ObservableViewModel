package com.rickclephas.kmm.viewmodel.compose

import androidx.compose.runtime.State as ComposeState

public expect interface State<out T>: ComposeState<T>
