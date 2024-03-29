package id.my.arieftb.soad.domain.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.auth.use_case.*
import id.my.arieftb.soad.domain.profile.repository.ProfileRepository
import id.my.arieftb.soad.domain.profile.use_case.CreateProfileUseCase
import id.my.arieftb.soad.domain.profile.use_case.CreateProfileUseCaseImpl
import id.my.arieftb.soad.domain.story.repository.StoryRepository
import id.my.arieftb.soad.domain.story.use_case.GetStoryCollectionUseCase
import id.my.arieftb.soad.domain.story.use_case.GetStoryCollectionUseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetStoryCollectionUseCase(
        @IO dispatcher: CoroutineDispatcher,
        repository: StoryRepository
    ): GetStoryCollectionUseCase {
        return GetStoryCollectionUseCaseImpl(dispatcher, repository)
    }

    @Provides
    fun provideCreateProfileUseCase(
        @IO dispatcher: CoroutineDispatcher,
        repository: ProfileRepository,
    ): CreateProfileUseCase {
        return CreateProfileUseCaseImpl(dispatcher, repository)
    }

    @Provides
    fun provideLogInAuthUseCase(
        @IO dispatcher: CoroutineDispatcher,
        repository: AuthRepository,
    ): LogInAuthUseCase {
        return LogInAuthUseCaseImpl(dispatcher, repository)
    }

    @Provides
    fun provideLogOutAuthUseCase(
        @IO dispatcher: CoroutineDispatcher,
        repository: AuthRepository,
    ): LogOutAuthUseCase {
        return LogOutAuthUseCaseImpl(dispatcher, repository)
    }

    @Provides
    fun provideGetAuthUseCase(
        @IO dispatcher: CoroutineDispatcher,
        repository: AuthRepository,
    ): GetAuthUseCase {
        return GetAuthUseCaseImpl(dispatcher, repository)
    }

    @Provides
    fun provideGetAuthStatusUseCase(
        @IO dispatcher: CoroutineDispatcher,
        getAuthUseCase: GetAuthUseCase,
    ): GetAuthStatusUseCase {
        return GetAuthStatusUseCaseImpl(dispatcher, getAuthUseCase)
    }
}
