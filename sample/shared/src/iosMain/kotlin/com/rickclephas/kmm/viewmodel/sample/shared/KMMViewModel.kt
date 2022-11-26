package com.rickclephas.kmm.viewmodel.sample.shared

actual abstract class KMMViewModel {
    actual abstract val viewModelScope: ViewModelScope
    actual open fun onCleared() { }
}
