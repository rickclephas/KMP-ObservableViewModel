package com.rickclephas.kmm.viewmodel.sample.shared

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }
}