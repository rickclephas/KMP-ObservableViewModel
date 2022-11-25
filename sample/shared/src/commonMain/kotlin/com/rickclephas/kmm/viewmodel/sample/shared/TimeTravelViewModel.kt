package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.coroutines.flow.*
import kotlin.random.Random

class TimeTravelViewModel: KMMViewModel() {

    override val viewModelScope = ViewModelScope(this)
    private val clockTime = Clock.time

    /**
     * A [StateFlow] that emits the actual time.
     */
    val actualTime = clockTime.map { formatTime(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "N/A")

    private val _travelEffect = MutableStateFlow<TravelEffect?>(viewModelScope, null)
    /**
     * A [StateFlow] that emits the applied [TravelEffect].
     */
    val travelEffect = _travelEffect.asStateFlow()

    /**
     * A [StateFlow] that indicates if the [currentTime] is fixed.
     * @see startTime
     * @see stopTime
     */
    val isFixedTime = _travelEffect.map { it is TravelEffect.Fixed }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    /**
     * A [StateFlow] that emits the current time.
     */
    val currentTime = combine(clockTime, _travelEffect) { actualTime, travelEffect ->
        formatTime(actualTime + travelEffect)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "N/A")

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
    fun resetTime() {
        _travelEffect.value = null
    }
}
