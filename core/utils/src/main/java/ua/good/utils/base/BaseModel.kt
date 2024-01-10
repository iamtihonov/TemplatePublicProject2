package ua.good.utils.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.good.utils.logs.logLifecycle

abstract class BaseModel : ViewModel() {

    init {
        logLifecycle("init")
    }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) { block() }
    }

    override fun onCleared() {
        super.onCleared()
        logLifecycle("onCleared")
    }

    fun onDestroy() {
        logLifecycle("onDestroy")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
