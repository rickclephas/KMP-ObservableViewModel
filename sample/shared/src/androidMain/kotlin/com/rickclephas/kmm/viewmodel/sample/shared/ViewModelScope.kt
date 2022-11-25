package com.rickclephas.kmm.viewmodel.sample.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual interface ViewModelScope

actual fun ViewModelScope(viewModel: KMMViewModel): ViewModelScope =
    ViewModelScopeImpl(viewModel)

actual val ViewModelScope.scope: CoroutineScope
    get() = (this as ViewModelScopeImpl).coroutineScope

private class ViewModelScopeImpl(viewModel: KMMViewModel): ViewModelScope {
    val coroutineScope: CoroutineScope = (viewModel as ViewModel).viewModelScope
}
