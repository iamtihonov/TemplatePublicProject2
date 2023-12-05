package ua.good.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import ua.good.utils.ResultWrapper
import java.net.UnknownHostException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeoutException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (error: TimeoutException) {
            Timber.e(error)
            ResultWrapper.NetworkError
        } catch (error: UnknownHostException) {
            Timber.e(error)
            ResultWrapper.NetworkError
        } catch (error: CancellationException) {
            Timber.e(error)
            ResultWrapper.CanceledError
        } catch (error: ErrorResponseException) {
            Timber.e(error)
            ResultWrapper.ServerError
        } catch (error: HttpException) {
            Timber.e(error)
            ResultWrapper.AuthorizedError
        }
    }
}
