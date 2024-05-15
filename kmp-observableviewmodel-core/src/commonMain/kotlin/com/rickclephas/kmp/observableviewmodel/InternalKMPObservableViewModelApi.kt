package com.rickclephas.kmp.observableviewmodel

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal KMP-ObservableViewModel API that shouldn't be used outside KMP-ObservableViewModel!"
)
@Retention(value = AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
public annotation class InternalKMPObservableViewModelApi
