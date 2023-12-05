package ua.good.network.requests

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ua.good.network.response.AuthorizationResponse
import ua.good.network.response.RepositoryResponse

@FunctionalInterface
interface BaseRequests {

    @POST("/authorizations")
    suspend fun authorize(
        @Header("Authorization") authorization: String,
        @Body params: JsonObject
    ): AuthorizationResponse

    @GET("/user/repos")
    suspend fun getRepositories(@Header("Authorization") authorization: String): ArrayList<RepositoryResponse>
}
