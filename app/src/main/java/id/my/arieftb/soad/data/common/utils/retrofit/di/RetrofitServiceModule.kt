package id.my.arieftb.soad.data.common.utils.retrofit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.soad.data.common.utils.retrofit.service.ProfileRemoteService
import retrofit2.Retrofit
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
}
