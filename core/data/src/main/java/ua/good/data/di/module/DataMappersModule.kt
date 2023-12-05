package ua.good.data.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.good.data.mappers.IRepositoryMapper
import ua.good.data.mappers.RepositoryMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataMappersModule {

    @Provides
    @Singleton
    fun provideRepositoryMapper(): IRepositoryMapper {
        return RepositoryMapper()
    }
}
