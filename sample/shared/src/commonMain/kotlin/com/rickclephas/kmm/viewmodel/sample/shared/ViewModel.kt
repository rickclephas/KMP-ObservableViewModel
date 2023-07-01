package com.rickclephas.kmm.viewmodel.sample.shared

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow

open class RootViewModel : KMMViewModel() {
    @NativeCoroutinesState
    val childViewModel: MutableStateFlow<TimeTravelViewModel> = MutableStateFlow(viewModelScope, TimeTravelViewModel())
}

open class ChildViewModel(name: String) : KMMViewModel() {
    @NativeCoroutinesState
    val name: MutableStateFlow<String> = MutableStateFlow(viewModelScope, name)
}
