package ua.good.db

import android.net.Uri
import androidx.room.TypeConverter
import kotlinx.datetime.Instant

/**
 * Пока сделан на будущее
 */
@Suppress("unused")
class DbParamMapper {

    @TypeConverter
    fun stringToUri(value: String): Uri? {
        return if (value.isEmpty()) {
            null
        } else {
            Uri.parse(value)
        }
    }

    @TypeConverter
    fun uriToString(status: Uri?): String {
        return status?.toString() ?: ""
    }

    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}
