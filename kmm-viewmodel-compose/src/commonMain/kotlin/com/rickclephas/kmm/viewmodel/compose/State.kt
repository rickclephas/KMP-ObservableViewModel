package com.rickclephas.kmm.viewmodel.compose

import kotlin.reflect.KProperty

public expect interface State<out T> {
    public val value: T
}

public inline operator fun <T : Any?> State<T>.getValue(
    thisObj: Any?,
    property: KProperty<*>
): T = value
