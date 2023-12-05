package ua.good.network.helper

import android.content.Context
import androidx.annotation.WorkerThread
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.good.network.IApiParamMapper
import ua.good.network.requests.BaseRequests
import ua.good.network.response.AuthorizationResponse
import ua.good.network.response.RepositoryResponse
import ua.good.network.safeApiCall
import ua.good.utils.BuildConfig
import ua.good.utils.ResultWrapper
import ua.good.utils.checkNotMainThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * В данный момент не работает, так как api менялся
 * Используется [DebugApiHelper]
 */
internal class ApiHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiParamMapper: IApiParamMapper
) : IApiHelper() {

    private val dispatcher = Dispatchers.IO
    private val baseRequests: BaseRequests by lazy { createBaseRequests() }

    @WorkerThread
    private fun createBaseRequests(): BaseRequests {
        checkNotMainThread()
        val okHttpClient = createOkHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(BaseRequests::class.java)
    }

    @WorkerThread
    fun createOkHttpClient(): OkHttpClient {
        checkNotMainThread()
        var builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            var logInterceptor = HttpLoggingInterceptor()
            logInterceptor = logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val chuckInterceptor = ChuckerInterceptor.Builder(context).build()
            builder = builder.addInterceptor(logInterceptor)
            builder = builder.addInterceptor(chuckInterceptor)
        }

        return builder.readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    override suspend fun login(
        login: String,
        passwordHash: String
    ): ResultWrapper<AuthorizationResponse> {
        val authorizationString = apiParamMapper.createAuthorizationString(login, passwordHash)
        val authorizationParam = apiParamMapper.createAuthorizationParam()
        return safeApiCall(dispatcher) {
            baseRequests.authorize(authorizationString, authorizationParam)
        }
    }

    override suspend fun loadUserRepositories(token: String): ResultWrapper<ArrayList<RepositoryResponse>> {
        val tokenString = apiParamMapper.createAuthorizationString(token)
        return safeApiCall(dispatcher) {
            baseRequests.getRepositories(tokenString)
        }
    }
}
