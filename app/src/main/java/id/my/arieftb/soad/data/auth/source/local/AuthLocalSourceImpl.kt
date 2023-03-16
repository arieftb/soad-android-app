package id.my.arieftb.soad.data.auth.source.local

import id.my.arieftb.soad.data.common.utils.data_store.service.AuthPreferenceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthLocalSourceImpl @Inject constructor(
    private val service: AuthPreferenceService
) : AuthLocalSource {
    override fun save(token: String): Flow<Boolean> {
        return flow {
            emit(service.create(token))
        }
    }

    override fun fetch(): Flow<String> {
        return flow {
            emit(service.fetch())
        }
    }
}
