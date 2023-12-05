package ua.good.network.response

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.Instant

/**
 * Содержит информацию о репозитории.
 */
class RepositoryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: String?,
    @SerializedName("instant") val instant: Instant
)
