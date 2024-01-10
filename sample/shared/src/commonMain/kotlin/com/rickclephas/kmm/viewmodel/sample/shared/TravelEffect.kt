package com.rickclephas.kmm.viewmodel.sample.shared

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
sealed class TravelEffect {
    @Serializable
    data class Offset(val offset: Long): TravelEffect()
    @Serializable
    data class Fixed(val time: Long): TravelEffect()
}

internal fun TravelEffect?.timeTravel(offset: Long): TravelEffect = when (this) {
    null -> TravelEffect.Offset(offset)
    is TravelEffect.Offset -> TravelEffect.Offset(this.offset + offset)
    is TravelEffect.Fixed -> TravelEffect.Fixed(this.time + offset)
}

internal fun TravelEffect?.startTime(time: Long): TravelEffect? = when (this) {
    null, is TravelEffect.Offset -> this
    is TravelEffect.Fixed -> TravelEffect.Offset(this.time - time)
}

internal fun TravelEffect?.stopTime(time: Long): TravelEffect = when (this) {
    is TravelEffect.Fixed -> this
    null -> TravelEffect.Fixed(time)
    is TravelEffect.Offset -> TravelEffect.Fixed(time + this.offset)
}

internal operator fun Long.plus(travelEffect: TravelEffect?): Long = when (travelEffect) {
    null -> this
    is TravelEffect.Fixed -> travelEffect.time
    is TravelEffect.Offset -> this + travelEffect.offset
}

internal fun Random.travelOffset(): Long = nextLong(-315_569_520L, 315_569_520L)
