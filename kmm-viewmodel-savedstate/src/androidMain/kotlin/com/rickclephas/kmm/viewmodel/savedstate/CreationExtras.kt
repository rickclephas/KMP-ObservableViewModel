package com.rickclephas.kmm.viewmodel.savedstate

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

public fun CreationExtras.createSavedStateHandle(): SavedStateHandle =
    SavedStateHandle(createSavedStateHandle())
