package id.my.arieftb.soad.domain.auth.repository

import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun logIn(email: String, password: String): Flow<ResultEntity<Boolean>>
    fun fetch(): Flow<ResultEntity<String>>
    fun delete(): Flow<ResultEntity<Boolean>>
}
