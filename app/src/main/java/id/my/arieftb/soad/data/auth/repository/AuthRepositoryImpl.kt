package id.my.arieftb.soad.data.auth.repository

import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRemoteResponse
import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRequest
import id.my.arieftb.soad.data.auth.source.remote.AuthRemoteSource
import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteSource
) : AuthRepository {
    override fun logIn(email: String, password: String): Flow<ResultEntity<Boolean>> {
        try {
            return remote.logIn(
                AuthLogInRequest(
                    email, password
                )
            ).catch {
                if (it is HTTPException) {
                    emit(AuthLogInRemoteResponse(true, null, it.message!!))
                    return@catch
                }

                throw it
            }.map {
                if (it.error) {
                    return@map ResultEntity.Failure(it.message)
                }

                return@map ResultEntity.Success(true)
            }
        } catch (e: Exception) {
            return flow {
                emit(ResultEntity.Error(e))
            }
        }
    }
}
