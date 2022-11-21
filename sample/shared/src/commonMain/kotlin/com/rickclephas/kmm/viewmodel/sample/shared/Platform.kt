package com.rickclephas.kmm.viewmodel.sample.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform