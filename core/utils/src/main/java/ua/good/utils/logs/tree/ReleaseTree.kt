package ua.good.utils.logs.tree

import timber.log.Timber

/**
 * Класс для Timber отправляющий логи на сервер
 */
class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // Нужно будет реализовать
    }
}
