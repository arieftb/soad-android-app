package id.my.arieftb.soad.data.auth.repository

import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRemoteResponse
import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRequest
import id.my.arieftb.soad.data.auth.source.local.AuthLocalSource
import id.my.arieftb.soad.data.auth.source.remote.AuthRemoteSource
import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteSource,
    private val local: AuthLocalSource,
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
            }.flatMapConcat {
                if (it.error) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Failure(it.message))
                    }
                }

                if (it.loginResult == null) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Success(false))
                    }
                }

                if (it.loginResult.token.isEmpty()) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Success(false))
                    }
                }

                local.save(it.loginResult.token).flatMapConcat {
                    flow {
                        emit(ResultEntity.Success(it))
                    }
                }
            }
        } catch (e: Exception) {
            return flow {
                emit(ResultEntity.Error(e))
            }
        }
    }

    override fun fetch(): Flow<ResultEntity<String>> {
        try {
            return local.fetch().map {
                return@map ResultEntity.Success(it)
            }
        } catch (e: Exception) {
            return flow {
                emit(ResultEntity.Error(e))
            }
        }
    }
}
