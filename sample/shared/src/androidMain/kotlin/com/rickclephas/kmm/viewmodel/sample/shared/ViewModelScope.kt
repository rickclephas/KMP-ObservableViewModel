package com.rickclephas.kmm.viewmodel.sample.shared

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual interface ViewModelScope: CoroutineScope

actual fun ViewModelScope(viewModel: KMMViewModel): ViewModelScope =
    ViewModelScopeImpl(viewModel.viewModelScope)

private class ViewModelScopeImpl(
    coroutineScope: CoroutineScope
): ViewModelScope, CoroutineScope by coroutineScope
