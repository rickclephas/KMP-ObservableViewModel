package com.rickclephas.kmp.observableviewmodel.sample.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
