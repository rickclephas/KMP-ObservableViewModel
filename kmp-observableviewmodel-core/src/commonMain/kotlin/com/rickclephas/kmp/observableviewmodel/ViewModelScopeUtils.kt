package com.rickclephas.kmp.observableviewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @see kotlinx.coroutines.isActive
 */
public inline val ViewModelScope.isActive: Boolean
    get() = coroutineScope.isActive

/**
 * @see kotlinx.coroutines.async
 */
public inline fun <T> ViewModelScope.async(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline block: suspend CoroutineScope.() -> T
): Deferred<T> = coroutineScope.async(context, start, block)

/**
 * @see kotlinx.coroutines.ensureActive
 */
public inline fun ViewModelScope.ensureActive(): Unit = coroutineScope.ensureActive()

/**
 * @see kotlinx.coroutines.launch
 */
public inline fun ViewModelScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    noinline block: suspend CoroutineScope.() -> Unit
): Job = coroutineScope.launch(context, start, block)

/**
 * @see kotlinx.coroutines.channels.produce
 */
@ExperimentalCoroutinesApi
public inline fun <E> ViewModelScope.produce(
    context: CoroutineContext = EmptyCoroutineContext,
    capacity: Int = 0,
    noinline block: suspend ProducerScope<E>.() -> Unit
): ReceiveChannel<E> = coroutineScope.produce(context, capacity, block)
