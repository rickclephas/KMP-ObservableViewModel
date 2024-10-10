package com.rickclephas.kmp.observableviewmodel.sample.shared

import kotlin.native.runtime.GC
import kotlin.native.runtime.NativeRuntimeApi

@OptIn(NativeRuntimeApi::class)
public fun gcCollect() {
    GC.collect()
}
