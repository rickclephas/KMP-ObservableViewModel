package com.rickclephas.kmm.viewmodel.savedstate

import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import kotlinx.serialization.*
import kotlinx.serialization.cbor.Cbor
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.posix.memcpy

@OptIn(ExperimentalSerializationApi::class)
internal val binaryFormat: BinaryFormat = Cbor {
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

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(UnsafeNumber::class)
internal fun Map<String, ByteArray?>.serialize(): NSData {
    val byteArray = binaryFormat.encodeToByteArray(this)
    return byteArray.usePinned {
        NSData.dataWithBytes(it.addressOf(0), byteArray.size.convert())
    }
}

@OptIn(UnsafeNumber::class)
internal fun NSData.deserialize(): Map<String, ByteArray?> {
    val byteArray = ByteArray(this.length.toInt()).apply {
        usePinned { memcpy(it.addressOf(0), this@deserialize.bytes, this@deserialize.length) }
    }
    return binaryFormat.decodeFromByteArray(byteArray)
}
