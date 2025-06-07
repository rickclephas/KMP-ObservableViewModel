package com.rickclephas.kmp.observableviewmodel.sample.shared

import platform.Foundation.*

internal actual fun epochSeconds(): Long = NSDate.date().timeIntervalSince1970().toLong()

private val timeFormatter = NSDateFormatter().apply {
    setLocalizedDateFormatFromTemplate("dd-MM-yyyy HH:mm:ss")
}

internal actual fun formatTime(epochSeconds: Long): String =
    timeFormatter.stringFromDate(NSDate.dateWithTimeIntervalSince1970(epochSeconds.toDouble()))
