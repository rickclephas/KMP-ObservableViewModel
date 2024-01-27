package com.rickclephas.kmm.viewmodel.savedstate

import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import java.io.Serializable
import kotlin.reflect.KClass

@PublishedApi
internal fun shouldSerialize(kClass: KClass<*>): Boolean = !nativeClasses.contains(kClass)

private val nativeClasses = setOfNotNull(
    Boolean::class, BooleanArray::class,
    Double::class, DoubleArray::class,
    Int::class, IntArray::class,
    Long::class, LongArray::class,
    String::class, Array<String>::class,
    Binder::class,
    Bundle::class,
    Byte::class, ByteArray::class,
    Char::class, CharArray::class,
    CharSequence::class, Array<CharSequence>::class,
    // ArrayList must be serialized as it can contain non-native classes
    Float::class, FloatArray::class,
    Parcelable::class, Array<Parcelable>::class,
    Serializable::class,
    Short::class, ShortArray::class,
    SparseArray::class,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) Size::class else null,
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) SizeF::class else null,
)

@OptIn(ExperimentalSerializationApi::class)
private val binaryFormat: BinaryFormat = Cbor {
    encodeDefaults = true
    ignoreUnknownKeys = true
}

@PublishedApi
internal fun <T> KSerializer<T>.serialize(value: T): ByteArray? {
    if (value == null) return null
    return binaryFormat.encodeToByteArray(this, value)
}

@PublishedApi
internal fun <T> KSerializer<T>.deserialize(value: ByteArray?): T {
    @Suppress("UNCHECKED_CAST")
    if (value == null) return null as T
    return binaryFormat.decodeFromByteArray(this, value)
}

@PublishedApi
internal fun <T> KSerializer<T>.deserialize(stateFlow: StateFlow<ByteArray?>): StateFlow<T> =
    DeserializeStateFlow(this, stateFlow)

private class DeserializeStateFlow<T>(
    private val serializer: KSerializer<T>,
    private val stateFlow: StateFlow<ByteArray?>
): StateFlow<T> {

    override val value: T get() = serializer.deserialize(stateFlow.value)

    override val replayCache: List<T> get() = listOf(value)

    override suspend fun collect(collector: FlowCollector<T>): Nothing =
        stateFlow.collect { collector.emit(serializer.deserialize(it)) }
}
