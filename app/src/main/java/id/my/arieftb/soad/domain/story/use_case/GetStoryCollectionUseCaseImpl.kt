package id.my.arieftb.soad.domain.story.use_case

import id.my.arieftb.soad.domain.common.exception.AuthenticationMissingException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.story.model.Story
import id.my.arieftb.soad.domain.story.model.request.StoryCollectionFetch
import id.my.arieftb.soad.domain.story.repository.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetStoryCollectionUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: StoryRepository
) : GetStoryCollectionUseCase {
    override fun execute(param: StoryCollectionFetch): Flow<ResultEntity<List<Story>>> {
        if (param.accessToken.isNullOrEmpty()) {
            return flow {
                ResultEntity.Error(AuthenticationMissingException())
            }
        }
        return try {
            repository.fetchCollection(param).catch { cause ->
                ResultEntity.Error(Exception(cause))
            }.flowOn(dispatcher)
        } catch (e: Exception) {
            flow {
                emit(ResultEntity.Error(e))
            }
        }
    }
}
