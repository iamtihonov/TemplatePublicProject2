package ua.good.utils.logs

import timber.log.Timber

fun Any.logLifecycle(methodName: String) {
    val message = "${javaClass.simpleName} $methodName"
    Timber.tag("application_lifecycle").i(message)
}

/**
 * todo - проверять diff и в debug сборке падать при росте или отправлять в SAGE
 */
fun Any.logDiff(startTime: Long) {
    val diff = System.currentTimeMillis() - startTime
    Timber.tag("application_lifecycle").e("%s injectTime = %s", javaClass.simpleName, diff)
}

/**
 * Используется с целью упрощения воспроизведения ошибки, полученной пользователем
 */
fun Any.logUi(message: String) {
    val resultMessage = "${javaClass.simpleName} $message"
    Timber.tag("application_ui").i(resultMessage)
}

fun logMainInfo(info: String) {
    Timber.tag("main_info").i(info)
}

fun logError(error: Throwable) {
    Timber.tag("rx_event").e(error)
}
