package id.my.arieftb.soad.data.profile.source.remote

import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRemoteResponse
import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRequest
import kotlinx.coroutines.flow.Flow

interface ProfileRemoteDataSource {
    fun create(param: ProfileCreateRequest): Flow<ProfileCreateRemoteResponse>
}
