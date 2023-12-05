package ua.good.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.good.utils.CryptoUtil
import ua.good.utils.ICryptoUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataHelpersModule {

    @Provides
    @Singleton
    fun provideDeviceInfoHelper(): ICryptoUtil {
        return CryptoUtil()
    }
}
