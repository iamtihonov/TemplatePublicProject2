package ua.good.model

import kotlinx.datetime.LocalDateTime
import java.util.Locale

/**
 * Размеры лучше всего хранить в байтах, а перед отображением конвертировать в нужный вид,
 * что бы можно было использовать эту информацию на других экранах по надобности.
 */
data class DeviceInfo(
    val deviceRooted: Boolean? = null,
    val deviceName: String = "",
    val applicationVersion: String = "",
    val navBarHeight: Int = 0,
    val screenSize: ScreenSize,
    val sdkInt: Int = 0,
    val density: Float = 0.0f,
    val startAppLocale: Locale = Locale.getDefault(),
    val startAppTime: String = "",
    val startAppTimeZone: String = "",
    val packageName: String = "",
    val batteryLevel: Int = 0,
    val startAppFontScale: Float = 0.0f,
    val orientationIsVertical: Boolean = false,
    val getFirstInstallTime: LocalDateTime? = null,
    val ramMemoryInfo: RamMemoryInfo,
    val internalMemoryInfo: MemoryInfo,
    val externalMemoryInfo: MemoryInfo
) {
    override fun toString(): String {
        return "Screen height = ${screenSize.height}" +
            " px, width = ${screenSize.width}" + " px, density = $density\n" +
            "Navigation bar height = $navBarHeight px\n" +
            "Start font scale = $startAppFontScale\n" +
            "Start orientationIsVertical = $orientationIsVertical\n" +
            "------------------------------------------------------------------------\n" +
            "Device rooted = $deviceRooted\n" +
            "Install time = $getFirstInstallTime\n" +
            "Time = $startAppTime\n" +
            "Time zone = $startAppTimeZone\n" +
            "Package name = $packageName\n" +
            "Application version = $applicationVersion\n" +
            "Device name = $deviceName\n" +
            "Api version = $sdkInt\n" +
            "Start language = ${startAppLocale.language}\n" +
            "------------------------------------------------------------------------\n" +
            "Used RAM memory (all device) $ramMemoryInfo\n" +
            "Internal memory $internalMemoryInfo\n" +
            "External memory $externalMemoryInfo\n" +
            "Battery level = $batteryLevel%"
    }
}
