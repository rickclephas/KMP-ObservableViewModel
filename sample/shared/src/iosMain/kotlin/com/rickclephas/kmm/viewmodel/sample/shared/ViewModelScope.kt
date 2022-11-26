package com.rickclephas.kmm.viewmodel.sample.shared

import com.rickclephas.kmm.viewmodel.combine.KMMVMObservableObjectScopeProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import platform.darwin.NSInteger
import platform.darwin.NSObject
import kotlin.native.ref.WeakReference

actual typealias ViewModelScope = KMMVMObservableObjectScopeProtocol

actual fun ViewModelScope(viewModel: KMMViewModel): ViewModelScope =
    ViewModelScopeImpl(WeakReference(viewModel))

actual val ViewModelScope.scope: CoroutineScope
    get() = (this as ViewModelScopeImpl).coroutineScope

internal class ViewModelScopeImpl(
    private val viewModelRef: WeakReference<KMMViewModel>
): NSObject(), ViewModelScope {
    val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val subscriptionCount = MutableStateFlow(0)

    override fun setSubscriptionCount(subscriptionCount: NSInteger) {
        this.subscriptionCount.value = subscriptionCount.toInt()
    }

    private var sendObjectWillChange: (() -> Unit)? = null

    override fun setSendObjectWillChange(sendObjectWillChange: () -> Unit) {
        this.sendObjectWillChange = sendObjectWillChange
    }

    fun sendObjectWillChange() {
        sendObjectWillChange?.invoke()
    }

    override fun callOnCleared() {
        viewModelRef.value?.onCleared()
    }
}
