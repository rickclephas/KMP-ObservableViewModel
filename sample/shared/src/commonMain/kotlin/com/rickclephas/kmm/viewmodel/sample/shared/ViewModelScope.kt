package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.CoroutineScope

expect interface ViewModelScope

expect fun ViewModelScope(viewModel: KMMViewModel): ViewModelScope

expect val ViewModelScope.scope: CoroutineScope
