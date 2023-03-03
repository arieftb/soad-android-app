package id.my.arieftb.soad.domain.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.my.arieftb.soad.domain.profile.repository.ProfileRepository
import id.my.arieftb.soad.domain.profile.use_case.CreateProfileUseCase
import id.my.arieftb.soad.domain.profile.use_case.CreateProfileUseCaseImpl
import id.my.arieftb.soad.domain.story.repository.StoryRepository
import id.my.arieftb.soad.domain.story.use_case.GetStoryCollectionUseCase
import id.my.arieftb.soad.domain.story.use_case.GetStoryCollectionUseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ActivityComponent::class)
class UseCaseModule {
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
}
