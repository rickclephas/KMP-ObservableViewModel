package com.rickclephas.kmp.observableviewmodel.sample.shared

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

open class TimeTravelViewModel: ViewModel() {

    private val clockTime = Clock.time

    /**
     * A [StateFlow] that emits the actual time.
     */
    @NativeCoroutinesState
    val actualTime: StateFlow<String> = clockTime.map { formatTime(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "N/A")

    private val _travelEffect = MutableStateFlow<TravelEffect?>(viewModelScope, null)
    /**
     * A [StateFlow] that emits the applied [TravelEffect].
     */
    @NativeCoroutinesState
    val travelEffect: StateFlow<TravelEffect?> = _travelEffect.asStateFlow()

    /**
     * A [StateFlow] that indicates if the [currentTime] is fixed.
     * @see startTime
     * @see stopTime
     */
    @NativeCoroutinesState
    val isFixedTime: StateFlow<Boolean> = _travelEffect.map { it is TravelEffect.Fixed }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    /**
     * A [StateFlow] that emits the current time.
     */
    @NativeCoroutinesState
    val currentTime: StateFlow<String> = combine(clockTime, _travelEffect) { actualTime, travelEffect ->
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
