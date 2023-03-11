package id.my.arieftb.soad.data.profile.source.remote

import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.data.common.utils.RemoteErrorMessageUtils
import id.my.arieftb.soad.data.common.utils.retrofit.service.ProfileRemoteService
import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRemoteResponse
import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRemoteDataSourceRetrofit @Inject constructor(
    private val profileRemoteService: ProfileRemoteService
) : ProfileRemoteDataSource {
    override fun create(param: ProfileCreateRequest): Flow<ProfileCreateRemoteResponse> {
        return flow {
            emit(profileRemoteService.create(param.toBody()))
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
