package ua.good.network

import android.util.Base64
import com.google.gson.JsonObject

/**
 * Утилита для генерации параметров при запросах. Вызывать только из DataManager.
 */
interface IApiParamMapper {
    fun createAuthorizationString(login: String, password: String): String

    fun createAuthorizationString(token: String): String

    fun createAuthorizationParam(): JsonObject
}

internal class ApiParamMapper : IApiParamMapper {
    override fun createAuthorizationString(login: String, password: String): String {
        val combinedStr = Base64.encodeToString("$login:$password".toByteArray(), Base64.DEFAULT)
        val authorizationString = BASIC_AUTHORIZATION + combinedStr
        return authorizationString.trim()
    }

    override fun createAuthorizationString(token: String): String {
        return "token $token"
    }

    override fun createAuthorizationParam(): JsonObject {
        val param = JsonObject()
        param.apply {
            addProperty(CLIENT_ID_PROPERTY, "GitHubClientId")
            addProperty(CLIENT_SECRET_PROPERTY, "GitHubClientSecret")
        }
        return param
    }

    private companion object {
        const val BASIC_AUTHORIZATION = "Basic "
        const val CLIENT_ID_PROPERTY = "client_id"
        const val CLIENT_SECRET_PROPERTY = "client_secret"
    }
}
