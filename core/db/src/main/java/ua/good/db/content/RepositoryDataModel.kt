package ua.good.db.content

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "repositories")
data class RepositoryDataModel(
    @PrimaryKey var id: Int = 0,
    var name: String,
    var language: String,
    var instant: Instant
)
