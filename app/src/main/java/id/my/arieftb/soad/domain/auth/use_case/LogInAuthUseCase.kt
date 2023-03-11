package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.Flow

interface LogInAuthUseCase {
    fun invoke(email: String, password: String): Flow<ResultEntity<Boolean>>
}
