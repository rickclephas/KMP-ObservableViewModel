package com.rickclephas.kmm.viewmodel.sample.shared

val TimeTravelViewModel.actualTimeValue: String
    get() = actualTime.value

val TimeTravelViewModel.travelEffectValue: TravelEffect?
    get() = travelEffect.value

val TimeTravelViewModel.isFixedTimeValue: Boolean
    get() = isFixedTime.value

val TimeTravelViewModel.currentTimeValue: String
    get() = currentTime.value
