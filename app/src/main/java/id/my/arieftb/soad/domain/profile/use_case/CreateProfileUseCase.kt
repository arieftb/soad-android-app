package id.my.arieftb.soad.domain.profile.use_case

import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.Flow

interface CreateProfileUseCase {
    fun execute(name: String, email: String, password: String): Flow<ResultEntity<Boolean>>
}
