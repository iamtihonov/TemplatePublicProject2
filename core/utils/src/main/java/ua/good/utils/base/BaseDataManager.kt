package ua.good.utils.base

import ua.good.utils.logs.logLifecycle

/**
 * Абстрактный класс менеджера данных
 */
abstract class BaseDataManager {

    init {
        logLifecycle("init")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
