package com.rickclephas.kmp.observableviewmodel

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

/**
 * A collection of [AutoCloseable]s behaving similar to the
 * [AndroidX ViewModel implementation](https://cs.android.com/androidx/platform/frameworks/support/+/58618bec2592c538b0a9e469f1492365ef2233e3:lifecycle/lifecycle-viewmodel/src/commonMain/kotlin/androidx/lifecycle/viewmodel/internal/ViewModelImpl.kt).
 */
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
