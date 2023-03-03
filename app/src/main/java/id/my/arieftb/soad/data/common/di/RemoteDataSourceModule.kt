package id.my.arieftb.soad.data.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.soad.data.common.utils.retrofit.service.ProfileRemoteService
import id.my.arieftb.soad.data.profile.source.remote.ProfileRemoteDataSource
import id.my.arieftb.soad.data.profile.source.remote.ProfileRemoteDataSourceRetrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideProfileRemoteDataSource(
        profileRemoteService: ProfileRemoteService
    ): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceRetrofit(profileRemoteService)
    }
}
