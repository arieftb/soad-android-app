package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.common.utils.format_validation.FormatValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LogInAuthUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: AuthRepository,
) : LogInAuthUseCase {
    override fun invoke(email: String, password: String): Flow<ResultEntity<Boolean>> {
        if (email.isEmpty()) {
            return flow {
                emit(ResultEntity.Error(EmailAttributeMissingException()))
            }
        }

        if (!FormatValidator.isEmail(email)) {
            return flow {
                emit(ResultEntity.Error(EmailWrongFormatException()))
            }
        }

        if (password.length < 8) {
            return flow {
                emit(ResultEntity.Error(PasswordSmallerThanException()))
            }
        }

        return try {
            repository.logIn(email, password).catch {
                emit(ResultEntity.Error(Exception(it)))
            }.flowOn(dispatcher)
        } catch (e: Exception) {
            flow {
                emit(ResultEntity.Error(e))
            }
        }
    }
}
