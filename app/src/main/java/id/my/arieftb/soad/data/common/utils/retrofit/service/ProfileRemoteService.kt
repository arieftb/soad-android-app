package id.my.arieftb.soad.data.common.utils.retrofit.service

import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRemoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

@JvmSuppressWildcards
interface ProfileRemoteService {
    @POST("v1/register")
    suspend fun create(
        @Body body: Map<String, Any>
    ): Response<ProfileCreateRemoteResponse>
}
