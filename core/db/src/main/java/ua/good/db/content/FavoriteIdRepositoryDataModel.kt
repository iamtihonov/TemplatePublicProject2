package ua.good.db.content

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Содержит информацию об избранных репозиториях
 */
@Entity
class FavoriteIdRepositoryDataModel(@PrimaryKey var id: Int)
