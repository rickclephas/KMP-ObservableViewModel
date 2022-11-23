package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.CoroutineScope

expect interface ViewModelScope: CoroutineScope

expect fun ViewModelScope(viewModel: KMMViewModel): ViewModelScope
