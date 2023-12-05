package ua.good.network.helper

import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import ua.good.network.response.AuthorizationResponse
import ua.good.network.response.RepositoryResponse
import ua.good.utils.ResultWrapper

/**
 * В реальном проекте парсить json
 * Если нужно будет когда самому генерировать json ответы, просто использовать библиотеку
 * Gson и скопировать полученные строки используя отладку
 */
internal class DebugApiHelper : IApiHelper() {

    override suspend fun login(login: String, passwordHash: String): ResultWrapper<AuthorizationResponse> {
        delay(1000)
        return if (login == "1" && passwordHash == DEBUG_PASSWORD_HASH) {
            ResultWrapper.Success(AuthorizationResponse(1, "test_token"))
        } else {
            ResultWrapper.AuthorizedError
        }
    }

    override suspend fun loadUserRepositories(token: String): ResultWrapper<ArrayList<RepositoryResponse>> {
        val result = ArrayList<RepositoryResponse>()

        for (index in 1..25) {
            result.add(createRepository(index, "Java"))
        }
        for (index in 26..50) {
            result.add(createRepository(index, "Kotlin"))
        }

        delay(1000)
        return ResultWrapper.Success(result)
    }

    private fun createRepository(index: Int, language: String): RepositoryResponse {
        return RepositoryResponse(index, "Name $index", language, Clock.System.now())
    }

    companion object {
        private const val DEBUG_PASSWORD_HASH = "1" // Для пароля = 1 так как пока не шифрую
    }
}
