package ua.good.model

import ua.good.model.base.Memory

data class MemoryInfo(val freeIbBytes: Long, val totalInBytes: Long) : Memory() {

    override fun toString(): String {
        return "free = " + format(freeIbBytes) + " from " + format(totalInBytes)
    }
}
