package ua.good.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.good.data.repositories.IAuthManager
import ua.good.data.repositories.IDeviceInfoManager
import ua.good.data.repositories.IRepositoriesManager
import ua.good.domain.GetRepositoriesUseCase
import ua.good.domain.IGetRepositoriesUseCase
import ua.good.domain.ILogDeviceInfoUseCase
import ua.good.domain.ILoginUseCase
import ua.good.domain.LogDeviceInfoUseCase
import ua.good.domain.LoginUseCase
import ua.good.utils.ICryptoUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(dataManager: IAuthManager, cryptoUtil: ICryptoUtil): ILoginUseCase {
        return LoginUseCase(dataManager, cryptoUtil)
    }

    @Provides
    @Singleton
    fun provideLogDeviceInfoUseCase(deviceInfoManager: IDeviceInfoManager): ILogDeviceInfoUseCase {
        return LogDeviceInfoUseCase(deviceInfoManager)
    }

    @Provides
    @Singleton
    fun provideGetRepositoriesUseCase(
        manager: IRepositoriesManager,
        loginUseCase: ILoginUseCase
    ): IGetRepositoriesUseCase {
        return GetRepositoriesUseCase(manager, loginUseCase)
    }
}
