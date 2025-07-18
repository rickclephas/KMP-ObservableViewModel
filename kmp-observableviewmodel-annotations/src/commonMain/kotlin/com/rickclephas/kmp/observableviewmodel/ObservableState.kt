package com.rickclephas.kmp.observableviewmodel

import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HidesFromObjC

/**
 * Identifies observable `StateFlow` properties.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
@OptIn(ExperimentalObjCRefinement::class)
@HidesFromObjC
public annotation class ObservableState
