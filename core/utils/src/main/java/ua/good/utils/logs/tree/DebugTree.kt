package ua.good.utils.logs.tree

import android.annotation.SuppressLint
import android.util.Log
import android.util.Log.ERROR
import android.util.Log.INFO
import timber.log.Timber

/**
 * Класс для Timber, для Debug сборки со стандартным тегом.
 * Так как жизненный цикл экранов известен, полный путь для всех сообщений не обязателен
 * FirebaseCrashlytics использую, все сообщения все равно будут в отдельном проекте Firebase
 */
class DebugTree : Timber.Tree() {

    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val usingTag = tag ?: DEFAULT_TAG
        if (t != null) {
            val errorText = "$message error = ${t.stackTraceToString()}"
            // Метод Log.e(usingTag, "error", throwable) не печатает в лог ошибки
            // UnknownHostException, вариант который сейчас используется печатает
            Log.e(usingTag, errorText)
            println(errorText)
        } else if (message.isNotEmpty()) {
            when (priority) {
                ERROR -> Log.e(usingTag, message)
                INFO -> Log.i(usingTag, message)
                else -> Log.d(usingTag, message)
            }
            println(message)
        }
    }

    companion object {
        private const val DEFAULT_TAG = "TemplateTag"
    }
}
