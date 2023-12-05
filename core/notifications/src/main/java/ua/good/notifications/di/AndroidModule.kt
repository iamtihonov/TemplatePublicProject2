package ua.good.notifications.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.good.notifications.INotificationsUtil
import ua.good.notifications.NotificationsUtil

@Module
@InstallIn(SingletonComponent::class)
class AndroidModule {

    @Provides
    fun provideNotificationsUtil(@ApplicationContext context: Context): INotificationsUtil {
        return NotificationsUtil(context)
    }
}
