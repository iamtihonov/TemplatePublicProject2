package ua.good.domain

import ua.good.data.repositories.IDeviceInfoManager
import ua.good.utils.logs.logMainInfo
import javax.inject.Inject

/**
 * Здесь так же можем добавлять данные в firebase для анализа ошибок
 */
interface ILogDeviceInfoUseCase {
    suspend fun invoke()
}

internal class LogDeviceInfoUseCase @Inject constructor(
    private val deviceInfoManager: IDeviceInfoManager
) : ILogDeviceInfoUseCase {

    override suspend fun invoke() {
        val deviceInfo = deviceInfoManager.getDeviceInfo()
        logMainInfo(deviceInfo.toString())
    }
}
