package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LogOutAuthUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: AuthRepository,
) : LogOutAuthUseCase {
    override fun invoke(): Flow<ResultEntity<Boolean>> {
        try {
            return repository.delete().catch {
                emit(ResultEntity.Error(Exception(it)))
            }.flatMapConcat {
                if (it is ResultEntity.Error) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Success(false))
                    }
                }

                if (it is ResultEntity.Failure) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Success(false))
                    }
                }

                return@flatMapConcat flow {
                    emit(
                        it
                    )
                }
            }.flowOn(dispatcher)
        } catch (e: Exception) {
            return flow {
                emit(ResultEntity.Success(false))
            }
        }
    }
}
