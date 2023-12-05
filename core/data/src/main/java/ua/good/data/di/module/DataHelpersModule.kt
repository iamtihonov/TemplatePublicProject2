package ua.good.data.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.good.data.helpers.DeviceInfoHelper
import ua.good.data.helpers.IDeviceInfoHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataHelpersModule {

    @Provides
    @Singleton
    fun provideDeviceInfoHelper(@ApplicationContext context: Context): IDeviceInfoHelper {
        return DeviceInfoHelper(context)
    }
}
