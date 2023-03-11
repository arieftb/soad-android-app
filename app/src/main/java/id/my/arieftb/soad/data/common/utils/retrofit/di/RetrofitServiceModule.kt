package id.my.arieftb.soad.data.common.utils.retrofit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.soad.data.auth.source.remote.AuthRemoteSourceRetrofit
import id.my.arieftb.soad.data.common.utils.retrofit.service.AuthRemoteService
import id.my.arieftb.soad.data.common.utils.retrofit.service.ProfileRemoteService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    @Singleton
    fun provideProfileRemoteService(
        retrofit: Retrofit
    ): ProfileRemoteService {
        return retrofit.create(ProfileRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteService(
        retrofit: Retrofit
    ): AuthRemoteService {
        return retrofit.create(AuthRemoteService::class.java)
    }
}
