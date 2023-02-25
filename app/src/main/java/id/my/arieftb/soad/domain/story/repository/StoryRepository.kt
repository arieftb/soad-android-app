package id.my.arieftb.soad.domain.story.repository

import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.story.model.Story
import id.my.arieftb.soad.domain.story.model.request.StoryCollectionFetch
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun fetchCollection(param: StoryCollectionFetch): Flow<ResultEntity<List<Story>>>
}
