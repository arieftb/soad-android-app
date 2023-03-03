package id.my.arieftb.soad.data.profile.source.remote

import id.my.arieftb.soad.data.common.utils.retrofit.service.ProfileRemoteService
import javax.inject.Inject

class ProfileRemoteDataSourceRetrofit @Inject constructor(
    private val profileRemoteService: ProfileRemoteService
) : ProfileRemoteDataSource
