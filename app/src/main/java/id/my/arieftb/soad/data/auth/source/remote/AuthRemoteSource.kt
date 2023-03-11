package id.my.arieftb.soad.data.auth.source.remote

import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRemoteResponse
import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRequest
import kotlinx.coroutines.flow.Flow

interface AuthRemoteSource {
    fun logIn(param: AuthLogInRequest): Flow<AuthLogInRemoteResponse>
}
