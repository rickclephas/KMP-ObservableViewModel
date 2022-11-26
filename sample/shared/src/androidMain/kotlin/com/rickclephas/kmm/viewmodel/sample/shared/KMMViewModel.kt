package com.rickclephas.kmm.viewmodel.sample.shared

import androidx.lifecycle.ViewModel

actual abstract class KMMViewModel: ViewModel() {
    actual abstract val viewModelScope: ViewModelScope
    public actual override fun onCleared() { }
}
