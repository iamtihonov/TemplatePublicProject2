package ua.good.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import ua.good.datastore.IntPreference
import ua.good.datastore.StringPreference
import ua.good.network.helper.IApiHelper
import ua.good.utils.ResultWrapper
import ua.good.utils.base.BaseDataManager
import javax.inject.Inject

abstract class IAuthManager : BaseDataManager() {
    abstract suspend fun loginUsingPasswordHash(login: String, passwordHash: String): ResultWrapper<Boolean>
    abstract suspend fun clearAuthorizedData()
    abstract suspend fun geCurrentUserLogin(): String
    abstract suspend fun geCurrentUserPasswordHash(): String
    abstract suspend fun setLogin(value: String)

    companion object {
        const val PREF_USER_LOGIN = "PREF_USER_DATA_1"
        const val PREF_USER_P = "PREF_USER_DATA_2"
        const val PREF_GIT_HUB_TOKEN = "PREF_USER_DATA_3"
        const val PREF_GIT_HUB_AUTH_ID = "PREF_USER_DATA_4"
    }
}

internal class AuthManager @Inject constructor(
    private val apiHelper: () -> IApiHelper,
    dataStore: DataStore<Preferences>
) : IAuthManager() {

    private var currentUserLogin = StringPreference(dataStore, PREF_USER_LOGIN)
    private var currentUserPassHash = StringPreference(dataStore, PREF_USER_P)
    private var gitHubToken = StringPreference(dataStore, PREF_GIT_HUB_TOKEN)
    private var gitHubAuthId = IntPreference(dataStore, PREF_GIT_HUB_AUTH_ID)

    override suspend fun loginUsingPasswordHash(login: String, passwordHash: String): ResultWrapper<Boolean> {
        val result = apiHelper().login(login, passwordHash)
        return if (result is ResultWrapper.Success) {
            val (id, token) = result.value
            if (token?.isNotEmpty() == true && id != null) {
                setAuthData(token, id, passwordHash)
                ResultWrapper.Success(true)
            } else {
                ResultWrapper.ServerError
            }
        } else {
            result.getError()
        }
    }

    private suspend fun setAuthData(token: String, id: Int, passwordHash: String) {
        gitHubToken.set(token)
        gitHubAuthId.set(id)
        currentUserPassHash.set(passwordHash)
    }

    override suspend fun clearAuthorizedData() {
        // Логин при выходе оставляем
        gitHubToken.set("")
        currentUserPassHash.set("")
    }

    override suspend fun setLogin(value: String) = currentUserLogin.set(value)

    override suspend fun geCurrentUserLogin() = currentUserLogin.get()

    override suspend fun geCurrentUserPasswordHash() = currentUserPassHash.get()
}
