package ua.good.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.good.db.content.FavoriteIdRepositoryDataModel
import ua.good.db.content.RepositoryDataModel
import ua.good.utils.logs.logLifecycle

@Suppress("EmptyMethod")
interface IDbHelper {
    fun repositoryDao(): RepositoryDao
}

@Database(
    entities = [RepositoryDataModel::class, FavoriteIdRepositoryDataModel::class],
    version = 4
)
@TypeConverters(DbParamMapper::class)
internal abstract class DbHelper : RoomDatabase(), IDbHelper {

    init {
        logLifecycle("init")
    }

    abstract override fun repositoryDao(): RepositoryDao

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
