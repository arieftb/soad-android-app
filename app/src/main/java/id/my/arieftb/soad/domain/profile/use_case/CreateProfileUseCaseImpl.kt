package id.my.arieftb.soad.domain.profile.use_case

import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.NameAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.common.utils.format_validation.FormatValidator
import id.my.arieftb.soad.domain.profile.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class CreateProfileUseCaseImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: ProfileRepository,
) : CreateProfileUseCase {
    override fun execute(
        name: String,
        email: String,
        password: String
    ): Flow<ResultEntity<Boolean>> {
        if (name.isEmpty()) {
            return flow {
                emit(ResultEntity.Error(NameAttributeMissingException()))
            }
        }

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

        try {
            return repository.create(name, email, password).catch { cause ->
                Timber.w(cause)
                ResultEntity.Success(false)
            }.flatMapConcat { createResult ->
                when (createResult) {
                    is ResultEntity.Success -> {
                        flow {
                            emit(createResult)
                        }
                    }
                    is ResultEntity.Failure -> {
                        Timber.w(createResult.message)
                        flow {
                            emit(createResult)
                        }
                    }
                    is ResultEntity.Error -> {
                        Timber.w(createResult.exception.message)
                        flow {
                            emit(ResultEntity.Success(false))
                        }
                    }
                }
            }.flowOn(dispatcher)
        } catch (e: Exception) {
            Timber.w(e)
            return flow {
                emit(ResultEntity.Success(false))
            }
        }
    }
}
