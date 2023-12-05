package ua.good.domain

import ua.good.data.repositories.IAuthManager
import ua.good.utils.ICryptoUtil
import ua.good.utils.ResultWrapper
import javax.inject.Inject

/**
 * Далее нужно добавлять AuthControlLayer или сделать это в качестве базового класса
 */
interface ILoginUseCase {
    suspend fun login(login: String, password: String): ResultWrapper<Boolean>
    suspend fun relogin(): ResultWrapper<Boolean>
    suspend fun clearAuthorizedData()
    suspend fun geCurrentUserLogin(): String
}

internal class LoginUseCase @Inject constructor(
    private val authManager: IAuthManager,
    private val cryptoUtil: ICryptoUtil
) : ILoginUseCase {

    override suspend fun login(login: String, password: String): ResultWrapper<Boolean> {
        authManager.setLogin(login)
        return authManager.loginUsingPasswordHash(login, cryptoUtil.encrypt(password))
    }

    override suspend fun relogin(): ResultWrapper<Boolean> {
        val login = authManager.geCurrentUserLogin()
        val userPassHash = authManager.geCurrentUserPasswordHash()
        return authManager.loginUsingPasswordHash(login, userPassHash)
    }

    override suspend fun clearAuthorizedData() {
        authManager.clearAuthorizedData()
    }

    override suspend fun geCurrentUserLogin() = authManager.geCurrentUserLogin()
}
