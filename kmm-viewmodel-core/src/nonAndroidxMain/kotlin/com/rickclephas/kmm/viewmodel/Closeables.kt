package com.rickclephas.kmm.viewmodel

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

@OptIn(ExperimentalStdlibApi::class)
internal class Closeables(
    private val closeables: MutableSet<AutoCloseable> = mutableSetOf(),
    private val keyedCloseables: MutableMap<String, AutoCloseable> = mutableMapOf()
): SynchronizedObject(), AutoCloseable {

    private var isClosed = false

    operator fun plusAssign(closeable: AutoCloseable): Unit = synchronized(this) {
        if (isClosed) {
            closeWithRuntimeException(closeable)
            return
        }
        closeables += closeable
    }

    operator fun set(key: String, closeable: AutoCloseable): Unit = synchronized(this) {
        if (isClosed) {
            closeWithRuntimeException(closeable)
            return
        }
        closeWithRuntimeException(keyedCloseables.put(key, closeable))
    }

    operator fun get(key: String): AutoCloseable? = synchronized(this) {
        keyedCloseables[key]
    }

    override fun close(): Unit = synchronized(this) {
        if (isClosed) return
        isClosed = true
        for (closeable in keyedCloseables.values) {
            closeWithRuntimeException(closeable)
        }
        for (closeable in closeables) {
            closeWithRuntimeException(closeable)
        }
        closeables.clear()
    }

    private fun closeWithRuntimeException(closeable: AutoCloseable?) {
        try {
            closeable?.close()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
