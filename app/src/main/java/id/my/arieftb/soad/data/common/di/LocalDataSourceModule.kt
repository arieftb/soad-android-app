package id.my.arieftb.soad.data.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.soad.data.auth.source.local.AuthLocalSource
import id.my.arieftb.soad.data.auth.source.local.AuthLocalSourceImpl
import id.my.arieftb.soad.data.common.utils.data_store.service.AuthPreferenceService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideAuthLocalSource(
        service: AuthPreferenceService
    ): AuthLocalSource {
        return AuthLocalSourceImpl(service)
    }
}
