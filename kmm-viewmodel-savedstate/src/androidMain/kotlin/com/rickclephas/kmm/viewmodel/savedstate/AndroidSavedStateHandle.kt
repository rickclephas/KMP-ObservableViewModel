package com.rickclephas.kmm.viewmodel.savedstate

public actual typealias AndroidSavedStateHandle = androidx.lifecycle.SavedStateHandle

public actual fun AndroidSavedStateHandle.asSavedStateHandle(): SavedStateHandle =
    SavedStateHandle(this)
