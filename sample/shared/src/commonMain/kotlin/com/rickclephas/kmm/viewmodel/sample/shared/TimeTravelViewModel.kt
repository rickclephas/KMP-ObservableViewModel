package com.rickclephas.kmm.viewmodel.sample.shared

import com.rickclephas.kmm.viewmodel.*
import com.rickclephas.kmm.viewmodel.savedstate.AndroidSavedStateHandle
import com.rickclephas.kmm.viewmodel.savedstate.SavedStateHandle
import com.rickclephas.kmm.viewmodel.savedstate.asSavedStateHandle
import com.rickclephas.kmm.viewmodel.savedstate.getMutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.*
import kotlin.random.Random

open class TimeTravelViewModel(
    val savedStateHandle: SavedStateHandle
): KMMViewModel() {
    constructor(androidSavedStateHandle: AndroidSavedStateHandle): this(androidSavedStateHandle.asSavedStateHandle())

    private val clockTime = Clock.time

    /**
     * A [StateFlow] that emits the actual time.
     */
    @NativeCoroutinesState
    val actualTime = clockTime.map { formatTime(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")

    private val _travelEffect = savedStateHandle.getMutableStateFlow<TravelEffect?>(viewModelScope, "travelEffect", null)
    /**
     * A [StateFlow] that emits the applied [TravelEffect].
     */
    @NativeCoroutinesState
    val travelEffect = _travelEffect.asStateFlow()

    /**
     * A [StateFlow] that indicates if the [currentTime] is fixed.
     * @see startTime
     * @see stopTime
     */
    @NativeCoroutinesState
    val isFixedTime = _travelEffect.map(viewModelScope, SharingStarted.WhileSubscribed()) {
        it is TravelEffect.Fixed
    }

    /**
     * A [StateFlow] that emits the current time.
     */
    @NativeCoroutinesState
    val currentTime = combine(clockTime, _travelEffect) { actualTime, travelEffect ->
        formatTime(actualTime + travelEffect)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")

    /**
     * Restarts the [currentTime] after has been previously stopped.
     * @see isFixedTime
     */
    fun startTime() {
        _travelEffect.update { it.startTime(clockTime.value) }
    }

    /**
     * Stops the [currentTime].
     * @see isFixedTime
     */
    fun stopTime() {
        _travelEffect.update { it.stopTime(clockTime.value) }
    }

    /**
     * Randomly travel between -10 years and +10 years.
     */
    fun timeTravel() {
        _travelEffect.update { it.timeTravel(Random.travelOffset()) }
    }

    /**
     * Resets the [currentTime] to [actualTime].
     */
    open fun resetTime() {
        _travelEffect.value = null
    }
}
