package com.rickclephas.kmm.viewmodel

@OptIn(ExperimentalStdlibApi::class)
internal class Closeables(
    private val closeables: MutableSet<AutoCloseable> = mutableSetOf(),
    private val keyedCloseables: MutableMap<String, AutoCloseable> = mutableMapOf()
): AutoCloseable { // TODO: lock

    private var isClosed = false

    operator fun plusAssign(closeable: AutoCloseable) {
        if (isClosed) {
            closeWithRuntimeException(closeable)
            return
        }
        closeables += closeable
    }

    operator fun set(key: String, closeable: AutoCloseable) {
        if (isClosed) {
            closeWithRuntimeException(closeable)
            return
        }
        closeWithRuntimeException(keyedCloseables.put(key, closeable))
    }

    operator fun get(key: String): AutoCloseable? = keyedCloseables[key]

    override fun close() {
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
