package ua.good.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.good.db.DbHelper
import ua.good.db.IDbHelper
import ua.good.db.RepositoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDbHelper(@ApplicationContext context: Context): IDbHelper {
        return Room.databaseBuilder(context, DbHelper::class.java, "database").build()
    }

    /**
     * Лучше выделять RepositoryDao, что бы код был более читаемым.
     */
    @Provides
    @Singleton
    fun provideRepositoryDao(dbHelper: IDbHelper): RepositoryDao {
        return dbHelper.repositoryDao()
    }
}
