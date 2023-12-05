package ua.good.utils

import android.os.Looper
import android.util.Log
import timber.log.Timber
import java.io.IOException

@Suppress("FunctionOnlyReturningConstant", "SameReturnValue")
fun currentBuildIsDev(): Boolean {
    return BuildConfig.DEBUG
}

fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}

@Suppress("unused")
fun getThreadName(): String {
    return Thread.currentThread().name
}

fun checkNotMainThread() {
    if (!isMainThread()) {
        return
    }

    val exception = IOException("Use not main thread!")
    if (currentBuildIsDev()) {
        throw exception
    } else if (!currentBuildIsDev()) {
        val error = Log.getStackTraceString(exception)
        Timber.tag("test_trace").e(error)
    }
}

@Suppress("unused")
fun <T> lazyUnsafe(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
