package ua.good.utils

import java.io.IOException

/**
 * todo Вероятно лучше перенести класс в отдельный модуль
 */
interface ICryptoUtil {
    fun encrypt(value: String): String
}

class CryptoUtil : ICryptoUtil {
    // На релизном проекте для passwordHash нужно реализовать полноценное шифрование
    override fun encrypt(value: String): String {
        if (!currentBuildIsDev()) {
            throw IOException()
        }
        return value
    }
}
