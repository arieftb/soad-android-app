package id.my.arieftb.soad.data.auth.source.remote

import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRemoteResponse
import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRequest
import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.data.common.utils.RemoteErrorMessageUtils
import id.my.arieftb.soad.data.common.utils.retrofit.service.AuthRemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRemoteSourceRetrofit @Inject constructor(
    private val authService: AuthRemoteService
) : AuthRemoteSource {
    override fun logIn(param: AuthLogInRequest): Flow<AuthLogInRemoteResponse> {
        return flow {
            emit(authService.logIn(param.toBody()))
        }.flatMapConcat {
            if (!it.isSuccessful) {
                return@flatMapConcat flow {
                    throw HTTPException(
                        it.code(),
                        RemoteErrorMessageUtils.toMessage(it.errorBody()?.charStream()!!)
                    )
                }
            }

            flow {
                emit(it.body()!!)
            }
        }
    }
}
