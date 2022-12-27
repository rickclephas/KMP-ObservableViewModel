package com.rickclephas.kmm.viewmodel.compose

import com.rickclephas.kmm.viewmodel.ViewModelScope

public expect class SnapshotStateList<T>: MutableList<T>

public expect fun <T> mutableStateListOf(viewModelScope: ViewModelScope): SnapshotStateList<T>

public expect fun <T> mutableStateListOf(viewModelScope: ViewModelScope, vararg elements: T): SnapshotStateList<T>
