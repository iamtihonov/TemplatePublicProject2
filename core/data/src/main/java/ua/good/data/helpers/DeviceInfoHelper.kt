package ua.good.data.helpers

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Point
import android.os.BatteryManager
import android.os.Build
import android.view.WindowManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ua.good.model.DeviceInfo
import ua.good.model.MemoryInfo
import ua.good.model.RamMemoryInfo
import ua.good.model.ScreenSize
import ua.good.utils.base.BaseHelper
import ua.good.utils.logs.logError
import java.io.File
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * todo Так же стоит вынести в отдельный модуль
 */
abstract class IDeviceInfoHelper : BaseHelper() {
    abstract fun getDeviceInfo(): DeviceInfo
}

internal class DeviceInfoHelper @Inject constructor(
    @ApplicationContext var context: Context
) : IDeviceInfoHelper() {

    override fun getDeviceInfo(): DeviceInfo {
        val resources = context.resources
        val configuration = resources.configuration

        val realScreenSize = getRealScreenSize()
        val timeZone = TimeZone.currentSystemDefault()
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as? BatteryManager
        val allDeviceRamMemoryInfo = getAllAppMemoryInfo(context)

        val deviceName = calculateDeviceName()
        val applicationVersion = calculateApplicationVersion()
        val navBarHeight = calculateNavBarHeight()
        val sdkInt: Int = Build.VERSION.SDK_INT
        val density: Float = resources.displayMetrics.density
        val locale: Locale = Locale.getDefault()
        val startAppTime: String = DateFormat.getDateTimeInstance().format(Date())
        val startAppTimeZone: String = timeZone.toString()
        val packageName = context.packageName
        val firstInstallTime = getFirstInstallTime()

        val startAppBatteryLevel = bm?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: -1
        val internalMemory = getInternalMemory()
        val externalMemory = getExternalMemory()

        val startAppFontScale: Float = configuration.fontScale
        val orientationIsVertical = configuration.orientation != Configuration.ORIENTATION_LANDSCAPE

        return DeviceInfo(
            isDeviceRooted(), deviceName, applicationVersion, navBarHeight,
            realScreenSize, sdkInt, density, locale, startAppTime,
            startAppTimeZone, packageName,
            startAppBatteryLevel, startAppFontScale, orientationIsVertical,
            firstInstallTime, allDeviceRamMemoryInfo, internalMemory, externalMemory
        )
    }

    @Suppress("SpellCheckingInspection")
    private fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
            "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
            "/system/sbin/", "/usr/bin/", "/vendor/bin/"
        )
        for (location in locations) {
            if (File(location + su).exists()) {
                return true
            }
        }
        return false
    }

    private fun getAllAppMemoryInfo(context: Context): RamMemoryInfo {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as? ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager?.getMemoryInfo(memoryInfo)
        return RamMemoryInfo(memoryInfo.totalMem, memoryInfo.availMem, memoryInfo.threshold, memoryInfo.lowMemory)
    }

    private fun getInternalMemory(): MemoryInfo {
        val file = File(context.filesDir.absoluteFile.toString())
        return MemoryInfo(file.freeSpace, file.totalSpace)
    }

    private fun getExternalMemory(): MemoryInfo {
        val file = File(context.getExternalFilesDir(null).toString())
        return MemoryInfo(file.freeSpace, file.totalSpace)
    }

    private fun calculateNavBarHeight(): Int {
        val appUsableSize = getAppUsableScreenSize()
        val realScreenSize = getRealScreenSize()

        // navigation bar on the side
        if (appUsableSize.width < realScreenSize.width) {
            return realScreenSize.width - appUsableSize.width
        }

        // navigation bar at the bottom
        return if (appUsableSize.height < realScreenSize.height) {
            realScreenSize.height - appUsableSize.height
        } else {
            0
        }
    }

    private fun getAppUsableScreenSize(): ScreenSize {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        return ScreenSize(size.x, size.y)
    }

    private fun getRealScreenSize(): ScreenSize {
        val metrics = context.resources.displayMetrics
        return ScreenSize(metrics.widthPixels, metrics.heightPixels)
    }

    private fun calculateDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s.isNullOrEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }

    private fun calculateApplicationVersion(): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            logError(e)
            ""
        }
    }

    private fun getFirstInstallTime(): LocalDateTime? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val instant = Instant.fromEpochMilliseconds(packageInfo.firstInstallTime)
            instant.toLocalDateTime(TimeZone.currentSystemDefault())
        } catch (e: PackageManager.NameNotFoundException) {
            logError(e)
            null
        }
    }
}
