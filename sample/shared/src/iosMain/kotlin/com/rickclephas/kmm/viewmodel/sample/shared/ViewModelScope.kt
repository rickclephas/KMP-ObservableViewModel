package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

actual interface ViewModelScope: CoroutineScope

actual fun ViewModelScope(
    viewModel: KMMViewModel
): ViewModelScope = ViewModelScopeImpl()

private class ViewModelScopeImpl: ViewModelScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main
}
