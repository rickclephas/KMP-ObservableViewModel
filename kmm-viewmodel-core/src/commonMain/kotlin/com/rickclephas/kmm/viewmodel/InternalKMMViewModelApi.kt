package com.rickclephas.kmm.viewmodel

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal KMM-ViewModel API that shouldn't be used used outside KMM-ViewModel!"
)
@Retention(value = AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class InternalKMMViewModelApi
