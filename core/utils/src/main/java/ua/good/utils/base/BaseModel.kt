package ua.good.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ua.good.utils.logs.logLifecycle

abstract class BaseModel : ViewModel() {

    init {
        logLifecycle("init")
    }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    /**
     * Базовый метод ViewModel
     */
    override fun onCleared() {
        super.onCleared()
        logLifecycle("onCleared")
    }

    fun onDestroy() {
        logLifecycle("onDestroy")
    }

    open fun onBackPressed() {
        logLifecycle("onBackPressed")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
