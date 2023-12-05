package ua.good.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.good.network.ApiParamMapper
import ua.good.network.IApiParamMapper
import ua.good.network.helper.DebugApiHelper
import ua.good.network.helper.IApiHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiHelper(): IApiHelper {
        return DebugApiHelper()
    }

    @Provides
    @Singleton
    fun provideApiParamMapper(): IApiParamMapper {
        return ApiParamMapper()
    }
}
