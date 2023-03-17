package id.my.arieftb.soad.data.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.arieftb.soad.data.auth.repository.AuthRepositoryImpl
import id.my.arieftb.soad.data.auth.source.local.AuthLocalSource
import id.my.arieftb.soad.data.auth.source.remote.AuthRemoteSource
import id.my.arieftb.soad.data.profile.repository.ProfileRepositoryImpl
import id.my.arieftb.soad.data.profile.source.remote.ProfileRemoteDataSource
import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.profile.repository.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideProfileRepository(
        remote: ProfileRemoteDataSource
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            remote
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        remote: AuthRemoteSource,
        local: AuthLocalSource,
    ): AuthRepository {
        return AuthRepositoryImpl(remote, local)
    }
}
