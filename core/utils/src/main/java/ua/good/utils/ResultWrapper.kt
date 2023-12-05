package ua.good.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data object NetworkError : ResultWrapper<Nothing>()
    data object ServerError : ResultWrapper<Nothing>()
    data object CanceledError : ResultWrapper<Nothing>()
    data object AuthorizedError : ResultWrapper<Nothing>()

    fun getError(): ResultWrapper<Nothing> {
        return when (this) {
            is NetworkError -> NetworkError
            is CanceledError -> CanceledError
            is AuthorizedError -> AuthorizedError
            else -> ServerError
        }
    }

    fun isSuccess() = this is Success
}
