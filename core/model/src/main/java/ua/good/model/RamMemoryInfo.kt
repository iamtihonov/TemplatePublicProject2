package ua.good.model

import ua.good.model.base.Memory

data class RamMemoryInfo(
    val totalMemoryInBytes: Long,
    val availMemoryInBytes: Long,
    val thresholdMemoryInBytes: Long,
    val isLowMemory: Boolean?
) : Memory() {

    override fun toString(): String {
        return "free = " + format(availMemoryInBytes) +
            " from " + format(totalMemoryInBytes) +
            " thresholdMemoryInBytes = ${format(thresholdMemoryInBytes)}" +
            " isLowMemory = $isLowMemory"
    }
}
