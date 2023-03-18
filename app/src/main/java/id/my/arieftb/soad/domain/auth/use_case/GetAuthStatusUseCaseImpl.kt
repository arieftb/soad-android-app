package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAuthStatusUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val getAuthUseCase: GetAuthUseCase,
) : GetAuthStatusUseCase {
    override fun invoke(): Flow<ResultEntity<Boolean>> {
        try {
            return getAuthUseCase.invoke().catch {
                emit(ResultEntity.Error(Exception(it)))
            }.flatMapConcat {
                if (it is ResultEntity.Success) {
                    if (it.data.isEmpty()) {
                        return@flatMapConcat flow {
                            emit(ResultEntity.Success(false))
                        }
                    }
                }

                if (it is ResultEntity.Failure) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Success(false))
                    }
                }

                if (it is ResultEntity.Error) {
                    return@flatMapConcat flow {
                        emit(ResultEntity.Success(false))
                    }
                }

                return@flatMapConcat flow {
                    emit(ResultEntity.Success(true))
                }
            }.flowOn(dispatcher)
        } catch (e: Exception) {
            return flow {
                emit(ResultEntity.Success(false))
            }
        }
    }
}
