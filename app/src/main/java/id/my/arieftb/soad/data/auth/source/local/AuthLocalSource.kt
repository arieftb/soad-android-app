package id.my.arieftb.soad.data.auth.source.local

import kotlinx.coroutines.flow.Flow

interface AuthLocalSource {
    fun save(token: String): Flow<Boolean>
    fun fetch(): Flow<String>
    fun delete(): Flow<Boolean>
}
