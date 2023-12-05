package ua.good.data.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.good.data.helpers.IDeviceInfoHelper
import ua.good.data.mappers.IRepositoryMapper
import ua.good.data.repositories.AuthManager
import ua.good.data.repositories.DeviceInfoManager
import ua.good.data.repositories.IAuthManager
import ua.good.data.repositories.IDeviceInfoManager
import ua.good.data.repositories.INotificationsManager
import ua.good.data.repositories.IRepositoriesManager
import ua.good.data.repositories.NotificationsManager
import ua.good.data.repositories.RepositoriesManager
import ua.good.db.RepositoryDao
import ua.good.network.helper.IApiHelper
import ua.good.notifications.INotificationsUtil
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ManagersModule {

    @Provides
    @Singleton
    fun provideDataManager(
        apiHelper: Provider<IApiHelper>,
        dataStore: DataStore<Preferences>
    ): IAuthManager {
        return AuthManager(apiHelper::get, dataStore)
    }

    @Provides
    @Singleton
    fun provideNotificationsManager(notificationsHelper: INotificationsUtil): INotificationsManager {
        return NotificationsManager(notificationsHelper)
    }

    @Provides
    @Singleton
    fun provideDeviceInfoManager(deviceInfoHelper: Provider<IDeviceInfoHelper>): IDeviceInfoManager {
        return DeviceInfoManager(deviceInfoHelper::get)
    }

    @Provides
    @Singleton
    fun provideRepositoriesManager(
        apiHelper: Provider<IApiHelper>,
        repositoryDao: Provider<RepositoryDao>,
        repositoryMapper: Provider<IRepositoryMapper>
    ): IRepositoriesManager {
        return RepositoriesManager(apiHelper::get, repositoryDao::get, repositoryMapper::get)
    }
}
