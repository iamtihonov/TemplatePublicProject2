package ua.good.data.repositories

import android.annotation.SuppressLint
import ua.good.data.helpers.IDeviceInfoHelper
import ua.good.model.DeviceInfo
import ua.good.utils.base.BaseDataManager
import javax.inject.Inject

abstract class IDeviceInfoManager : BaseDataManager() {
    abstract suspend fun getDeviceInfo(): DeviceInfo
}

@SuppressLint("CheckResult")
internal class DeviceInfoManager @Inject constructor(
    private val deviceInfoHelperProvider: () -> IDeviceInfoHelper
) : IDeviceInfoManager() {

    override suspend fun getDeviceInfo(): DeviceInfo {
        return deviceInfoHelperProvider().getDeviceInfo()
    }
}
