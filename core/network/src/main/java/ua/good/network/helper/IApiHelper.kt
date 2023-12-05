package ua.good.network.helper

import ua.good.network.response.AuthorizationResponse
import ua.good.network.response.RepositoryResponse
import ua.good.utils.ResultWrapper
import ua.good.utils.base.BaseHelper

abstract class IApiHelper : BaseHelper() {
    abstract suspend fun login(login: String, passwordHash: String): ResultWrapper<AuthorizationResponse>
    abstract suspend fun loadUserRepositories(token: String): ResultWrapper<ArrayList<RepositoryResponse>>
}
