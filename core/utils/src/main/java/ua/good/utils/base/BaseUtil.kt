package ua.good.utils.base

import ua.good.utils.logs.logLifecycle

abstract class BaseUtil {

    init {
        logLifecycle("init")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
