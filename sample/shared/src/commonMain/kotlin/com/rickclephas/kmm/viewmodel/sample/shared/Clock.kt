package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

@OptIn(DelicateCoroutinesApi::class)
internal object Clock {
    val time = flow {
        while (true) {
            emit(epochSeconds())
            delay(1.seconds)
        }
    }.stateIn(GlobalScope, SharingStarted.Eagerly, 0L)
}

internal expect fun epochSeconds(): Long

internal expect fun formatTime(epochSeconds: Long): String
