package ua.artem.template.application.extensions

import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Вспомогательные классы для Throwable
 */
private fun <T : Exception> isIt(tClass: Class<T>, t: Throwable): Boolean {
    if (t.javaClass == tClass) {
        return true
    }
    return false
}

@Suppress("unused")
fun Throwable.isNetworkError(): Boolean {
    return isIt(UnknownHostException::class.java, this) ||
        isIt(TimeoutException::class.java, this)
}

@Suppress("unused")
fun Throwable.from(): Exception {
    return this as? Exception ?: Exception(this)
}
