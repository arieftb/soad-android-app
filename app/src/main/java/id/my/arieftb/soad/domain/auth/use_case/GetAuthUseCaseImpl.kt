package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAuthUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: AuthRepository,
) : GetAuthUseCase {
    override fun invoke(): Flow<ResultEntity<String>> {
        return try {
            repository.fetch().catch { emit(ResultEntity.Error(Exception(it))) }
                .flowOn(dispatcher)
        } catch (e: Exception) {
            flow {
                emit(ResultEntity.Error(e))
            }
        }
    }
}
