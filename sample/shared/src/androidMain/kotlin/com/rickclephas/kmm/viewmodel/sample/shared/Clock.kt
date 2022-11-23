package com.rickclephas.kmm.viewmodel.sample.shared

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

internal actual fun epochSeconds(): Long = System.currentTimeMillis() / 1_000

private val timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

internal actual fun formatTime(epochSeconds: Long): String =
    LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC).format(timeFormatter)
