package id.my.arieftb.soad.data.profile.repository

import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRemoteResponse
import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRequest
import id.my.arieftb.soad.data.profile.source.remote.ProfileRemoteDataSource
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remote: ProfileRemoteDataSource
) : ProfileRepository {
    override fun create(
        name: String,
        email: String,
        password: String
    ): Flow<ResultEntity<Boolean>> {
        try {
            return remote.create(
                ProfileCreateRequest(
                    name, email, password
                )
            ).catch { cause ->
                if (cause is HTTPException) {
                    ProfileCreateRemoteResponse(true, cause.message!!)
                } else {
                    flow {
                        emit(ResultEntity.Error(Exception(cause)))
                    }
                }
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
