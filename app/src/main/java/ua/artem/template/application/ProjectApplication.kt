package ua.artem.template.application

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import ua.artem.template.BuildConfig
import ua.good.domain.ILogDeviceInfoUseCase
import ua.good.utils.logs.logDiff
import ua.good.utils.logs.logLifecycle
import ua.good.utils.logs.tree.DebugTree
import ua.good.utils.logs.tree.ReleaseTree
import javax.inject.Inject

/**
 * Базовый класс для поддержания глобального состояния приложения.
 * Статический контекст хорошо бы не использовать
 */
@HiltAndroidApp
class ProjectApplication : Application() {

    @Inject
    lateinit var logDeviceInfoUseCase: ILogDeviceInfoUseCase

    init {
        logLifecycle("init")
    }

    /**
     * https://proandroiddev.com/improving-android-startup-initializing-dagger-async-f8193b48f834
     */
    override fun onCreate() {
        super.onCreate()
        initStrictMode()
        val startTime = System.currentTimeMillis()
        logDeviceInfo()
        initTimber()
        logDiff(startTime)
    }

    private fun logDeviceInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            logDeviceInfoUseCase.invoke()
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else if (!BuildConfig.DEBUG) {
            Timber.plant(ReleaseTree())
        }
    }

    /**
     * Если вызывать до super.onCreate() то ловиться ошибка.
     * Учитывать что он не все вызовы в Main потоке обнаруживает. Проверить что логирование
     * работает можно посмотреть вывод по логу StrictMode.
     */
    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            val threadPolicy = StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .penaltyDeath()
                .build()
            StrictMode.setThreadPolicy(threadPolicy)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logLifecycle("onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        logLifecycle("onTrimMemory, level = $level")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
