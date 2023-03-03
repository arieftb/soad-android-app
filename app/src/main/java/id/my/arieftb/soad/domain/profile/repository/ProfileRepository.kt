package id.my.arieftb.soad.domain.profile.repository

import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun create(name: String, email: String, password: String): Flow<ResultEntity<Boolean>>
}
