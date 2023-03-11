package id.my.arieftb.soad.data.common.utils.retrofit.service

import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRemoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface AuthRemoteService {
    @POST("v1/login")
    suspend fun logIn(
        @Body body: Map<String, Any>
    ): Response<AuthLogInRemoteResponse>
}
