package ua.good.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ua.good.db.content.FavoriteIdRepositoryDataModel
import ua.good.db.content.RepositoryDataModel

/**
 * Класс отвечающий за взаимодействие с таблицей RepositoryResponse
 */
@Dao
abstract class RepositoryDao {

    @Insert
    abstract fun insert(items: List<RepositoryDataModel>)

    @Insert
    abstract fun insert(item: FavoriteIdRepositoryDataModel)

    @Delete
    abstract fun delete(item: FavoriteIdRepositoryDataModel)

    @Query("DELETE FROM repositories")
    abstract fun clearAll()

    @Query("SELECT * FROM repositories")
    abstract fun getRepositories(): List<RepositoryDataModel>

    fun update(items: List<RepositoryDataModel>) {
        clearAll()
        insert(items)
    }
}
