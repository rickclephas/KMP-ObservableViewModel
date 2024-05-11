package com.rickclephas.kmp.observableviewmodel.sample.shared

class Greeting {
    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }
}
