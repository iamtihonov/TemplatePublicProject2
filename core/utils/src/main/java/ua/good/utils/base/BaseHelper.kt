package ua.good.utils.base

import ua.good.utils.logs.logLifecycle

abstract class BaseHelper {

    init {
        logLifecycle("init")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
