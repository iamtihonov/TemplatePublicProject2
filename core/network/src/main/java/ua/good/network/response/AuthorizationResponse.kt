package ua.good.network.response

import com.google.gson.annotations.SerializedName

/**
 * Ответ от сервера об авторизации
 */
data class AuthorizationResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("token") val token: String?
)
