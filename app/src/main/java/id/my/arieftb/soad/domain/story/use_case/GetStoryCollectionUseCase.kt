package id.my.arieftb.soad.domain.story.use_case

import id.my.arieftb.soad.domain.common.use_case.UseCase
import id.my.arieftb.soad.domain.story.model.Story
import id.my.arieftb.soad.domain.story.model.request.StoryCollectionFetch

interface GetStoryCollectionUseCase : UseCase<StoryCollectionFetch, List<Story>>
