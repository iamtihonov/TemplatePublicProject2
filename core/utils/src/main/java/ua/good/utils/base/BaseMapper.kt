package ua.good.utils.base

import ua.good.utils.logs.logLifecycle

abstract class BaseMapper<T1, T2, T3> {

    init {
        logLifecycle("init")
    }

    abstract fun mapToModel(input: T1): T2

    abstract fun mapFromModel(input: T2): T3

    fun mapToModel(values: List<T1>): List<T2> {
        val returnValues = ArrayList<T2>(values.size)
        for (value in values) {
            returnValues.add(mapToModel(value))
        }
        return returnValues
    }

    fun mapFromModel(values: List<T2>): List<T3> {
        val returnValues = ArrayList<T3>(values.size)
        for (value in values) {
            returnValues.add(mapFromModel(value))
        }
        return returnValues
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
